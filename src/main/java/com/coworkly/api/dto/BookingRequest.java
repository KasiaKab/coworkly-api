package com.coworkly.api.dto;

import java.time.OffsetDateTime;

public record BookingRequest(
        Long resourceId,
        OffsetDateTime start,
        OffsetDateTime end,
        String email // optional for v0
) {}
