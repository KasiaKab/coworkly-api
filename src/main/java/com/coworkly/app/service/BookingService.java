package com.coworkly.app.service;

import com.coworkly.app.exception.NotFoundException;
import com.coworkly.app.exception.SlotAlreadyBookedException;
import com.coworkly.domain.entity.Booking;
import com.coworkly.domain.entity.BookingStatus;
import com.coworkly.domain.repository.BookingRepository;
import com.coworkly.domain.repository.ResourceRepository;
import com.coworkly.domain.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ResourceRepository resourceRepository;

    /**
     * Create a booking if the time slot is available.
     * Business rules (M00):
     *  - resource & timeSlot must exist
     *  - timeSlot must belong to the given resource
     *  - no active (BOOKED) booking may exist for that timeSlot
     *  - start/end are derived from the timeSlot
     */
    @Transactional
    public Booking create(Booking booking) {
        Long resourceId = booking.getResource().getId();
        Long timeSlotId = booking.getTimeSlot().getId();

        // [A] Load MANAGED references (not transient stubs)
        var resourceRef = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new NotFoundException("Resource not found: " + resourceId));

        var slotRef = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new NotFoundException("TimeSlot not found: " + timeSlotId));

        // [B] Validate ownership: slot must belong to the resource
        if (!slotRef.getResource().getId().equals(resourceId)) {
            throw new NotFoundException("TimeSlot " + timeSlotId + " does not belong to Resource " + resourceId);
        }

        // [C] Conflict only if there exists an ACTIVE booking for that slot
        if (bookingRepository.existsByTimeSlotIdAndStatus(timeSlotId, BookingStatus.BOOKED)) {
            throw new SlotAlreadyBookedException(timeSlotId); // handled as 409 in GlobalExceptionHandler
        }

        // [D] Replace transient refs with managed and derive times from slot
        booking.setResource(resourceRef);
        booking.setTimeSlot(slotRef);
        booking.setStartAt(slotRef.getStartAt());
        booking.setEndAt(slotRef.getEndAt());

        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.BOOKED);
        }

        return bookingRepository.save(booking);
    }

    /** Find booking by id. */
    @Transactional(readOnly = true)
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    /** Find booking by Idempotency-Key (if present). */
    @Transactional(readOnly = true)
    public Optional<Booking> findByIdempotencyKey(String key) {
        if (key == null || key.isBlank()) return Optional.empty();
        return bookingRepository.findByIdempotencyKey(key);
    }

    /**
     * Idempotent creation:
     *  - if key already used → return existing booking
     *  - otherwise set key and delegate to create(...)
     */
    @Transactional
    public Booking createIdempotent(Booking booking, String idempotencyKey) {
        var existing = findByIdempotencyKey(idempotencyKey);
        if (existing.isPresent()) {
            return existing.get();
        }
        booking.setIdempotencyKey(idempotencyKey);
        return create(booking);
    }
}
