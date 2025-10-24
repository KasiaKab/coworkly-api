-- V4__booking_unique_on_active.sql
ALTER TABLE booking
DROP CONSTRAINT IF EXISTS uk_booking_resource_time;

DROP INDEX IF EXISTS uk_booking_resource_time;

ALTER TABLE booking
DROP CONSTRAINT IF EXISTS uk_booking_time_slot;

DROP INDEX IF EXISTS uk_booking_time_slot;

CREATE UNIQUE INDEX IF NOT EXISTS ux_booking_slot_booked
    ON booking (time_slot_id)
    WHERE status = 'BOOKED';

CREATE UNIQUE INDEX IF NOT EXISTS ux_booking_idempotency_key
    ON booking (idempotency_key)
    WHERE idempotency_key IS NOT NULL;