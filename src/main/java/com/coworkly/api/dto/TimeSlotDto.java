package com.coworkly.api.dto;

// Uses OffsetDateTime instead of LocalDateTime to include the time zone offset (e.g. +02:00).
import java.time.OffsetDateTime;

public record TimeSlotDto(
        Long id,
        Long resourceId,
        OffsetDateTime start,
        OffsetDateTime end
) {}
