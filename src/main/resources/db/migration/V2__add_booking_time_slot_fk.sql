-- V2__add_booking_time_slot_fk.sql
-- 1) CREATE TABLE time_slot (jeśli jeszcze nie ma)
CREATE TABLE IF NOT EXISTS time_slot (
                                         id           BIGSERIAL PRIMARY KEY,
                                         resource_id  BIGINT       NOT NULL,
                                         start_at     TIMESTAMPTZ  NOT NULL,
                                         end_at       TIMESTAMPTZ  NOT NULL
);

-- FK time_slot.resource_id -> resource(id)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_time_slot_resource'
    ) THEN
ALTER TABLE time_slot
    ADD CONSTRAINT fk_time_slot_resource
        FOREIGN KEY (resource_id) REFERENCES resource (id);
END IF;
END $$;

-- pomocniczy indeks do zapytań po zasobie i starcie
CREATE INDEX IF NOT EXISTS idx_time_slot_resource_start
    ON time_slot (resource_id, start_at);

-- 2) booking.time_slot_id + więzy zgodnie z encją
ALTER TABLE booking
    ADD COLUMN IF NOT EXISTS time_slot_id BIGINT;

ALTER TABLE booking
    ALTER COLUMN time_slot_id SET NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'uk_booking_time_slot'
    ) THEN
ALTER TABLE booking
    ADD CONSTRAINT uk_booking_time_slot UNIQUE (time_slot_id);
END IF;
END $$;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'fk_booking_time_slot'
    ) THEN
ALTER TABLE booking
    ADD CONSTRAINT fk_booking_time_slot
        FOREIGN KEY (time_slot_id) REFERENCES time_slot (id);
END IF;
END $$;
