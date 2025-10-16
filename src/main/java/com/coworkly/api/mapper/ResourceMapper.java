package com.coworkly.api.mapper;

import com.coworkly.api.dto.ResourceDto;
import com.coworkly.domain.entity.Resource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceMapper {

    // ENTITY -> DTO
    public static ResourceDto toDto(Resource entity) {
        if (entity == null) return null;
        return new ResourceDto(
                entity.getId(),
                entity.getName(),
                entity.getCapacity(),
                csvToList(entity.getFeatures())
        );
    }

    // DTO -> ENTITY
    public static Resource toEntity(ResourceDto dto) {
        if (dto == null) return null;
        return Resource.builder()
                .id(dto.id())
                .name(dto.name())
                .capacity(dto.capacity())
                .features(listToCsv(dto.features()))
                .build();
    }


    // --- Helpers: CSV <-> List conversion ---

    public static List<String> csvToList(String csv) {
        if (csv == null || csv.isBlank()) return Collections.emptyList();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    public static String listToCsv(List<String> features) {
        if (features == null || features.isEmpty()) return null;
        String joined = features.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        return joined.isEmpty() ? null : joined;
    }
}