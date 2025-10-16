package com.coworkly.api.dto;

import java.time.OffsetDateTime;

public record BookingRequest(
        Long resourceId,
        Long timeSlotId,
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        String email // optional for v0
) {}
