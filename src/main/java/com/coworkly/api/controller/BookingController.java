package com.coworkly.api.controller;

import com.coworkly.api.dto.BookingDto;
import com.coworkly.api.dto.BookingRequest;
import com.coworkly.api.dto.BookingResponse;
import com.coworkly.api.mapper.BookingMapper;
import com.coworkly.app.exception.NotFoundException;
import com.coworkly.app.service.BookingService;
import com.coworkly.domain.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class BookingController {

    private final BookingService bookingService;

    // POST /bookings — create booking with Idempotency-Key support
    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> create(
            @RequestBody BookingRequest request,
            @RequestHeader(name = "Idempotency-Key", required = false) String idempotencyKey
    ) {
        // Require the header for safety
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Idempotency-Key header is required");
        }

        // Return existing booking if the key already exists (200 OK)
        var existing = bookingService.findByIdempotencyKey(idempotencyKey);
        if (existing.isPresent()) {
            BookingResponse response = BookingMapper.toResponse(existing.get());
            return ResponseEntity.ok(response);
        }

        // Otherwise create new booking (201 Created)
        Booking toCreate = BookingMapper.toEntity(request);
        Booking booking = bookingService.createIdempotent(toCreate, idempotencyKey);
        BookingResponse response = BookingMapper.toResponse(booking);

        return ResponseEntity
                .created(URI.create("/bookings/" + booking.getId()))
                .body(response);
    }

    // GET /bookings/{id} — get booking details
    @GetMapping("/bookings/{id}")
    public BookingDto getById(@PathVariable("id") Long id) {
        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found: " + id));
        return BookingMapper.toDto(booking);
    }
}
