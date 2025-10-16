package com.coworkly.api.dto;

import java.time.OffsetDateTime;

public record BookingResponse(
        Long id,
        Long resourceId,
        Long timeSlotId,
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        String status  // "BOOKED" / "CANCELLED"
) {}
