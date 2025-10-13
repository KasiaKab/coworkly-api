package com.coworkly.api.mapper;

import com.coworkly.api.dto.BookingDto;
import com.coworkly.api.dto.BookingRequest;
import com.coworkly.api.dto.BookingResponse;
import com.coworkly.entity.Booking;
import com.coworkly.entity.Resource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BookingMapper {

    // ENTITY -> DTO
    public static BookingDto toDto(Booking entity) {
        if (entity == null) return null;

        Long resourceId = null;
        if (entity.getResource() != null) {
            resourceId = entity.getResource().getId();
        }

        return new BookingDto(
                entity.getId(),
                resourceId,
                entity.getStartAt(),
                entity.getEndAt(),
                entity.getStatus()
        );
    }

    // ENTITY -> RESPONSE
    public static BookingResponse toResponse(Booking entity) {
        if (entity == null) return null;

        Long resourceId = null;
        if (entity.getResource() != null) {
            resourceId = entity.getResource().getId();
        }

        return new BookingResponse(
                entity.getId(),
                resourceId,
                entity.getStartAt(),
                entity.getEndAt(),
                entity.getStatus()
        );
    }

    // REQUEST -> ENTITY
    public static Booking toEntity(BookingRequest request, Resource resource) {
        if (request == null) return null;
        return Booking.builder()
                .userEmail(request.email())
                .resource(resource)
                .startAt(request.start())
                .endAt(request.end())
                .status("BOOKED")
                .createdAt(OffsetDateTime.now().toString())
                .build();
    }
}
