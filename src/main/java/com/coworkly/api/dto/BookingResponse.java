package com.coworkly.api.dto;

import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Long resourceId,
        LocalDateTime start,
        LocalDateTime end,
        String status
) {}
