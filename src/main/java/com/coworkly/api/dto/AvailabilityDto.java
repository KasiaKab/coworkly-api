package com.coworkly.api.dto;

import java.time.LocalDateTime;

public record AvailabilityDto(
        Long resourceId,
        LocalDateTime start,
        LocalDateTime end
) {}
