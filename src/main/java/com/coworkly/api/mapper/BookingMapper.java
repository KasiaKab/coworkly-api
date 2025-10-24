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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookingMapper {

    // ENTITY -> DTO (safe for LAZY: times taken from Booking, not TimeSlot)
    public static BookingDto toDto(Booking entity) {
        if (entity == null) return null;

        Long resourceId = (entity.getResource() != null) ? entity.getResource().getId() : null;
        Long timeSlotId = (entity.getTimeSlot() != null) ? entity.getTimeSlot().getId() : null;
        String status   = (entity.getStatus() != null)   ? entity.getStatus().name()    : null;

        return new BookingDto(
                entity.getId(),
                resourceId,
                timeSlotId,
                entity.getStartAt(),
                entity.getEndAt(),
                status
        );
    }

    // ENTITY -> RESPONSE (safe for LAZY: times taken from Booking, not TimeSlot)
    public static BookingResponse toResponse(Booking entity) {
        if (entity == null) return null;

        Long resourceId = (entity.getResource() != null) ? entity.getResource().getId() : null;
        Long timeSlotId = (entity.getTimeSlot() != null) ? entity.getTimeSlot().getId() : null;
        String status   = (entity.getStatus() != null)   ? entity.getStatus().name()    : null;

        return new BookingResponse(
                entity.getId(),
                resourceId,
                timeSlotId,
                entity.getStartAt(),
                entity.getEndAt(),
                status
        );
    }

    // REQUEST -> ENTITY (with managed references supplied by service)
    public static Booking toEntity(BookingRequest request, Resource resource, TimeSlot slot) {
        if (request == null) return null;

        Booking booking = new Booking();
        booking.setUserEmail(request.email());
        booking.setResource(resource);
        booking.setTimeSlot(slot);

        if (slot != null) {
            booking.setStartAt(slot.getStartAt());
            booking.setEndAt(slot.getEndAt());
        }

        booking.setStatus(BookingStatus.BOOKED);
        return booking;
    }

    // REQUEST -> ENTITY (IDs only; service SHOULD replace with managed refs before save)
    public static Booking toEntity(BookingRequest dto) {
        Resource resourceRef = Resource.builder().id(dto.resourceId()).build();
        TimeSlot slotRef     = TimeSlot.builder().id(dto.timeSlotId()).build();
        return toEntity(dto, resourceRef, slotRef);
    }
}
