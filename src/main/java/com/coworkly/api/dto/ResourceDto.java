package com.coworkly.api.dto;

import java.util.List;

public record ResourceDto(
        Long id,
        String name,
        Integer capacity,
        List<String> features
) {}
