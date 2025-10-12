package com.coworkly.api.dto;

public record ResourceDto(
        Long id,
        String name,
        Integer capacity,
        String features // comma-separated for v0
) {}
