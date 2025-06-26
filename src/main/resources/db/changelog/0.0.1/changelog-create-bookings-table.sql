--liquibase formatted sql

--changeset MelnykVL:create-bookings-table
--comment create table booking_system.bookings
CREATE TYPE BOOKING_STATUS AS ENUM('RESERVED', 'PAID', 'COMPLETED', 'CANCELED', 'EXPIRED');

CREATE TABLE booking_system.bookings (
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    unit_id             BIGINT REFERENCES booking_system.units(id),
    user_id             BIGINT REFERENCES booking_system.users(id),
    check_in_on         DATE NOT NULL,
    check_out_on        DATE NOT NULL, --exclusion
    total_price         NUMERIC(10, 2) NOT NULL,
    status              BOOKING_STATUS NOT NULL,
    expires_at          TIMESTAMPTZ NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ NOT NULL,
    period              DATERANGE GENERATED ALWAYS AS (daterange(check_in_on, check_out_on, '[)')) stored
);
--rollback drop table booking_system.bookings

--changeset MelnykVL:create-constraint-cho_chin_check
--comment create constraint cho_chin_check
ALTER TABLE booking_system.bookings
ADD CONSTRAINT cho_chin_check CHECK (check_out_on > check_in_on)
--rollback drop constraint cho_chin_check

--changeset MelnykVL:create-constraint-booking_no_overlap
--comment create constraint booking_no_overlap
ALTER TABLE booking_system.bookings
ADD CONSTRAINT booking_no_overlap EXCLUDE USING gist(unit_id WITH =, period WITH &&) WHERE (status IN ('RESERVED', 'PAID'))
--rollback drop constraint booking_no_overlap

--changeset MelnykVL:create-index-idx_booking_expire
--comment create index idx_booking_expire
CREATE INDEX idx_booking_expire ON booking_system.bookings(expires_at) WHERE status = 'RESERVED';
--rollback drop index idx_booking_expire

--changeset MelnykVL:create-index-idx_booking_complete
--comment create index idx_booking_complete
CREATE INDEX idx_booking_complete ON booking_system.bookings(check_out_on) WHERE status = 'PAID'
--rollback drop index idx_booking_complete