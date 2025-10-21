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

    // Returns a new booking if the time slot is available
    @Transactional
    public Booking create(Booking booking) {
        Long resourceId = booking.getResource().getId();
        Long timeSlotId = booking.getTimeSlot().getId();

        // Validate resource and time slot existence
        if (!resourceRepository.existsById(resourceId)) {
            throw new NotFoundException("Resource not found: " + resourceId);
        }
        if (!timeSlotRepository.existsById(timeSlotId)) {
            throw new NotFoundException("TimeSlot not found: " + timeSlotId);
        }

        // Check if the time slot is already booked
        if (bookingRepository.existsByTimeSlotId(timeSlotId)) {
            throw new SlotAlreadyBookedException(timeSlotId);
        }

        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.BOOKED);
        }

        return bookingRepository.save(booking);
    }

    // Returns a booking by its ID or empty optional if not found.
    @Transactional
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    // Look up an existing booking by Idempotency-Key.
    public Optional<Booking> findByIdempotencyKey(String key) {
        if (key == null || key.isBlank())
            return Optional.empty();
        return bookingRepository.findByIdempotencyKey(key);
    }

    // Create booking in an idempotent way.
    @Transactional
    public Booking createIdempotent(Booking booking, String idempotencyKey) {
        // Return existing if the key was already used
        var existing = findByIdempotencyKey(idempotencyKey);
        if (existing.isPresent())
            return existing.get();

        // Set key on new booking and reuse existing validation flow
        booking.setIdempotencyKey(idempotencyKey);
        return create(booking);
    }
}
