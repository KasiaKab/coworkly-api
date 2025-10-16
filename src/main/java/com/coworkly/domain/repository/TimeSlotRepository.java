package com.coworkly.domain.repository;

import com.coworkly.domain.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findAllByResourceIdAndStartBetween(Long resourceId, OffsetDateTime dayStart, OffsetDateTime dayEnd);
}
