-- V3__add_idempotency_key_to_booking.sql
ALTER TABLE booking
    ADD COLUMN IF NOT EXISTS idempotency_key VARCHAR(64);

CREATE UNIQUE INDEX IF NOT EXISTS uk_booking_idempotency_key
    ON booking (idempotency_key)
    WHERE idempotency_key IS NOT NULL;