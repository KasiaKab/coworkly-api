package com.coworkly.api.dto;

import java.time.LocalDateTime;

public record BookingRequest(
        Long resourceId,
        LocalDateTime start,
        LocalDateTime end,
        String email // optional for v0
) {}
