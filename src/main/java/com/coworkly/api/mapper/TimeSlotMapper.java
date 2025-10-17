package com.coworkly.api.mapper;


import com.coworkly.api.dto.AvailabilitySlotDto;
import com.coworkly.api.dto.TimeSlotDto;
import com.coworkly.domain.entity.Resource;
import com.coworkly.domain.entity.TimeSlot;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeSlotMapper {

    // ENTITY -> DTO (full timeslot)
    public static TimeSlotDto toDto(TimeSlot entity) {
        Long resourceId;
        if (entity.getResource() != null) {
            resourceId = entity.getResource().getId();
        } else {
            resourceId = null;
        }

        return new TimeSlotDto(
                entity.getId(),
                resourceId,
                entity.getStartAt(),
                entity.getEndAt()
        );
    }

    public static TimeSlot toEntity(TimeSlotDto dto, Resource resource) {
        if (dto == null) return null;
        return TimeSlot.builder()
                .id(dto.id())
                .resource(resource)
                .startAt(dto.startAt())
                .endAt(dto.endAt())
                .build();
    }

    // DTO -> ENTITY (availability view for GET /resources/{id}/availability)
    public static AvailabilitySlotDto toAvailabilityDto(TimeSlot entity) {
        Long resourceId;
        if (entity.getResource() != null) {
            resourceId = entity.getResource().getId();
        } else {
            resourceId = null;
        }

        boolean available = (entity.getBooking() == null);
        return new AvailabilitySlotDto(
                entity.getStartAt(),
                entity.getEndAt(),
                available
        );
    }
}
