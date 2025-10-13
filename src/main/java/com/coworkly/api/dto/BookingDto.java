package com.coworkly.api.dto;

import java.time.OffsetDateTime;

public record BookingDto(
    Long id,
    Long resourceId,
    OffsetDateTime start,
    OffsetDateTime end,
    String status // "BOOKED"/"CANCELLED"
) {}
