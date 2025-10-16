package com.coworkly.api.dto;

import java.time.OffsetDateTime;

public record BookingDto(
    Long id,
    Long resourceId,
    Long timeSlotId,
    OffsetDateTime startAt,
    OffsetDateTime endAt,
    String status // "BOOKED"/"CANCELLED"
) {}
