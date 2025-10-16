package com.coworkly.app.service;

import com.coworkly.app.exception.NotFoundException;
import com.coworkly.domain.entity.Resource;
import com.coworkly.domain.entity.TimeSlot;
import com.coworkly.domain.repository.ResourceRepository;
import com.coworkly.domain.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final TimeSlotRepository timeSlotRepository;

    // Returns all resources (rooms/desks) from the system.
    @Transactional
    public List<Resource> listResources() {
        return resourceRepository.findAll();
    }


    // Returns all available time slots for a given resource on a specific date.
    @Transactional
    public List<TimeSlot> findAvailablility(Long resourceId, LocalDate date) {
        if (!resourceRepository.existsById(resourceId)) {
            throw new NotFoundException("Resource not found: " + resourceId);
        }

        /*
         *  |---------------------- 2025-10-14 ----------------------|
         *   2025-10-14T00:00+02:00                            2025-10-15T00:00+02:00
         *              ↑ startOfDay                                      ↑ endOfDay
         */
        OffsetDateTime startOfDay = date.atStartOfDay().atOffset(OffsetDateTime.now().getOffset());
        OffsetDateTime endOfDay = startOfDay.plusDays(1);

        List<TimeSlot> slots = timeSlotRepository
                .findAllByResourceIdAndStartAtBetween(resourceId, startOfDay, endOfDay);

        return slots.stream()
                .filter(slot -> slot.getBooking() == null) // Only available slots
                .toList();
    }

}
