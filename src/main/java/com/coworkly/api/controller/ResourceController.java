package com.coworkly.api.controller;

import com.coworkly.app.service.ResourceService;
import com.coworkly.api.dto.AvailabilitySlotDto;
import com.coworkly.api.dto.ResourceDto;
import com.coworkly.api.mapper.ResourceMapper;
import com.coworkly.api.mapper.TimeSlotMapper;
import com.coworkly.domain.entity.Resource;
import com.coworkly.domain.entity.TimeSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ResourceController {

    private final ResourceService resourceService;

    // GET /resources — list all resources
    @GetMapping("/resources")
    public List<ResourceDto> listResources() {
        List<Resource> resources = resourceService.listResources();
        return resources.stream()
                .map(ResourceMapper::toDto)
                .toList();
    }

    // GET /resources/{id}/availability?date=YYYY-MM-DD — available slots for given day
    @GetMapping("/resources/{resourceId}/availability")
    public List<AvailabilitySlotDto> getAvailability(
            @PathVariable("resourceId") Long resourceId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<TimeSlot> freeSlots = resourceService.findAvailablility(resourceId, date); // uwaga: zgodnie z nazwą w serwisie
        return freeSlots.stream()
                .map(TimeSlotMapper::toAvailabilityDto)
                .toList();
    }
}
