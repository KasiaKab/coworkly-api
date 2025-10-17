package com.coworkly.api.controller;

import com.coworkly.api.dto.BookingDto;
import com.coworkly.api.dto.BookingRequest;
import com.coworkly.api.dto.BookingResponse;
import com.coworkly.api.mapper.BookingMapper;
import com.coworkly.app.exception.NotFoundException;
import com.coworkly.app.service.BookingService;
import com.coworkly.domain.entity.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class BookingController {

    private final BookingService bookingService;

    // POST /bookings — create new booking
    @PostMapping("/bookings")
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request) {
        Booking toCreate = BookingMapper.toEntity(request);
        Booking booking = bookingService.create(toCreate);

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
