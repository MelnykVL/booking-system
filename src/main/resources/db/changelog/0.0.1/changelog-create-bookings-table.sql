--liquibase formatted sql

--changeset MelnykVL:create-bookings-table
--comment create table booking_system.bookings
CREATE TYPE BOOKING_STATUS AS ENUM('RESERVED', 'PAID', 'CANCELED', 'EXPIRED');

CREATE TABLE booking_system.bookings (
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    unit_id             BIGINT REFERENCES booking_system.units(id),
    user_id             BIGINT REFERENCES booking_system.users(id),
    check_in_on         DATE NOT NULL,
    check_out_on        DATE NOT NULL, --exclusion
    total_cost          NUMERIC(10, 2) NOT NULL,
    status              BOOKING_STATUS NOT NULL,
    expires_at          TIMESTAMPTZ NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ NOT NULL,
    period              DATERANGE GENERATED ALWAYS AS (daterange(check_in_on, check_out_on, '[)')) stored,
    CONSTRAINT cho_chin_check CHECK ( check_out_on > check_in_on ),
    CONSTRAINT booking_no_overlap EXCLUDE USING gist(unit_id WITH =, period WITH &&) WHERE (status IN ('RESERVED', 'PAID'))
);
--rollback drop table booking_system.bookings