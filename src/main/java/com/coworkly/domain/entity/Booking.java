package com.coworkly.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "booking",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_booking_resource_time", columnNames = {"resource_id", "start_at", "end_at"})
        },
        indexes = {
                @Index(name = "idx_booking_resource_start", columnList = "resource_id, start_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String userEmail;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_resource"))
    private Resource resource;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_booking_time_slot"))
    private TimeSlot timeSlot;

    @Column(name = "start_at", nullable = false)
    private OffsetDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private OffsetDateTime endAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BookingStatus status;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "idempotency_key", unique = true, length = 64)
    private String idempotencyKey;

    @PrePersist
    void prePersist() {
        if (status == null) {
            status = BookingStatus.BOOKED;
        }
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}
