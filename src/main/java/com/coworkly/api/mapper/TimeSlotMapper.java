package com.coworkly.api.mapper;

import com.coworkly.api.dto.TimeSlotDto;
import com.coworkly.entity.Resource;
import com.coworkly.entity.TimeSlot;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeSlotMapper {

    // ENTITY -> DTO
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
                entity.getStart(),
                entity.getEnd()
        );
    }

    // DTO -> ENTITY
    public static TimeSlot toEntity(TimeSlotDto dto, Resource resource) {
        if (dto == null) return null;
        return TimeSlot.builder()
                .id(dto.id())
                .resource(resource)
                .start(dto.start())
                .end(dto.end())
                .build();
    }
}
