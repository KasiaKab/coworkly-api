-- V1 initial migration script

CREATE TABLE IF NOT EXISTS resource (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL UNIQUE,
    capacity INTEGER NOT NULL,
    features VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS booking (
    id BIGSERIAL PRIMARY KEY,
    user_email   VARCHAR(255) NOT NULL,
    resource_id  BIGINT NOT NULL REFERENCES resource(id) ON DELETE RESTRICT,
    start_at     TIMESTAMPTZ NOT NULL,
    end_at       TIMESTAMPTZ NOT NULL,
    status       VARCHAR(20) NOT NULL DEFAULT 'BOOKED',
    created_at   TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_booking_resource_time
    ON booking(resource_id, start_at, end_at);

CREATE INDEX IF NOT EXISTS idx_booking_resource_start
    ON booking(resource_id, start_at);