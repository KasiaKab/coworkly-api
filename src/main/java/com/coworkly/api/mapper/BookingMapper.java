package com.coworkly.api.mapper;

import com.coworkly.api.dto.BookingDto;
import com.coworkly.api.dto.BookingRequest;
import com.coworkly.api.dto.BookingResponse;
import com.coworkly.domain.entity.Booking;
import com.coworkly.domain.entity.BookingStatus;
import com.coworkly.domain.entity.Resource;
import com.coworkly.domain.entity.TimeSlot;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookingMapper {

    // ENTITY -> DTO
    public static BookingDto toDto(Booking entity) {
        if (entity == null) return null;

        Long resourceId = null;
        Long timeSlotId = null;
        OffsetDateTime start = null;
        OffsetDateTime end = null;
        String status = null;

        if (entity.getResource() != null) {
            resourceId = entity.getResource().getId();
        }
        if (entity.getTimeSlot() != null) {
            timeSlotId = entity.getTimeSlot().getId();
            start = entity.getTimeSlot().getStartAt();
            end = entity.getTimeSlot().getEndAt();
        }

        // ENUM -> STRING
        if (entity.getStatus() != null) {
            status = entity.getStatus().name();
        }

        return new BookingDto(
                entity.getId(),
                resourceId,
                timeSlotId,
                start,
                end,
                status
        );
    }

    // ENTITY -> RESPONSE
    public static BookingResponse toResponse(Booking entity) {
        if (entity == null) return null;

        Long resourceId = null;
        Long timeSlotId = null;
        OffsetDateTime start = null;
        OffsetDateTime end = null;
        String status = null;

        if (entity.getResource() != null) {
            resourceId = entity.getResource().getId();
        }
        if (entity.getTimeSlot() != null) {
            timeSlotId = entity.getTimeSlot().getId();
            start = entity.getTimeSlot().getStartAt();
            end = entity.getTimeSlot().getEndAt();
        }

        // ENUM -> STRING
        if (entity.getStatus() != null) {
            status = entity.getStatus().name();
        }

        return new BookingResponse(
                entity.getId(),
                resourceId,
                timeSlotId,
                start,
                end,
                status
        );
    }

    // REQUEST -> ENTITY
    public static Booking toEntity(BookingRequest request, Resource resource, TimeSlot timeSlot) {
        if (request == null) return null;

        Booking booking = new Booking();
        booking.setUserEmail(request.email());
        booking.setResource(resource);
        booking.setTimeSlot(timeSlot);

        if (timeSlot != null) {
            booking.setStartAt(timeSlot.getStartAt());
            booking.setEndAt(timeSlot.getEndAt());
        }

        booking.setStatus(BookingStatus.BOOKED);

        return booking;
    }
}
