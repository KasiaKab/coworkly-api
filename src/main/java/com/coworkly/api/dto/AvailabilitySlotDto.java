package com.coworkly.api.dto;

import java.time.OffsetDateTime;

public record AvailabilitySlotDto(
        OffsetDateTime start,
        OffsetDateTime end
) {}
