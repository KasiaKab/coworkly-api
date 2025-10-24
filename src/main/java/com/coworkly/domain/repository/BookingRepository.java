package com.coworkly.domain.repository;

import com.coworkly.domain.entity.Booking;
import com.coworkly.domain.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByTimeSlotIdAndStatus(Long timeSlotId, BookingStatus status);

    Optional<Booking> findByIdempotencyKey(String idempotencyKey);

}
