-- DEV SEED (Repeatable) — Coworkly API v0.1.0 (M00)
-- Entities: resource, time_slot, booking
-- Safe for re-run: uses ON CONFLICT DO NOTHING

-- RESOURCES (200–299)
INSERT INTO resource (id, name, capacity, features)
VALUES
    (200, 'Desk A1', 1, 'Near window'),
    (201, 'Desk A2', 1, 'Standard desk'),
    (202, 'Meeting Room 1', 6, 'TV, Whiteboard'),
    (203, 'Conference Room', 12, 'Projector, AC'),
    (204, 'Phone Booth 1', 1, 'Soundproof booth')
    ON CONFLICT (id) DO NOTHING;

-- TIME SLOTS (300–399)
INSERT INTO time_slot (id, resource_id, start_at, end_at)
VALUES
    (300, 200, '2025-10-22T08:00:00+02', '2025-10-22T10:00:00+02'),
    (301, 201, '2025-10-22T10:00:00+02', '2025-10-22T12:00:00+02'),
    (302, 202, '2025-10-22T13:00:00+02', '2025-10-22T15:00:00+02'),
    (303, 203, '2025-10-23T08:00:00+02', '2025-10-23T10:00:00+02'),
    (304, 204, '2025-10-23T10:00:00+02', '2025-10-23T12:00:00+02')
    ON CONFLICT (id) DO NOTHING;

-- BOOKINGS (400–499)
INSERT INTO booking (id, user_email, resource_id, start_at, end_at, status, created_at, time_slot_id)
VALUES
    (400, 'alice@coworkly.dev', 200, '2025-10-22T08:00:00+02', '2025-10-22T10:00:00+02', 'BOOKED',    now(), 300),
    (401, 'alice@coworkly.dev', 201, '2025-10-22T10:00:00+02', '2025-10-22T12:00:00+02', 'BOOKED',    now(), 301),
    (402, 'bob@coworkly.dev',   202, '2025-10-22T13:00:00+02', '2025-10-22T15:00:00+02', 'CANCELLED', now(), 302),
    (403, 'bob@coworkly.dev',   203, '2025-10-23T08:00:00+02', '2025-10-23T10:00:00+02', 'BOOKED',    now(), 303),
    (404, 'admin@coworkly.dev', 204, '2025-10-23T10:00:00+02', '2025-10-23T12:00:00+02', 'BOOKED',    now(), 304)
    ON CONFLICT (id) DO NOTHING;
