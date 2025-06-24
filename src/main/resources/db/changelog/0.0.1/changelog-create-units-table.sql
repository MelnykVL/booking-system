--liquibase formatted sql

--changeset MelnykVL:create-units-table
--comment create table booking_system.units
CREATE TYPE ACCOMMODATION_TYPE AS ENUM('HOME', 'FLAT', 'APARTMENTS');

CREATE TABLE booking_system.units (
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    owner_id            BIGINT REFERENCES booking_system.users(id),
    number_of_rooms     INTEGER NOT NULL,
    floor               INTEGER NOT NULL,
    type                ACCOMMODATION_TYPE NOT NULL,
    price_per_night     NUMERIC(10, 2) NOT NULL,
    description         TEXT,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ NOT NULL
);
--rollback drop table booking_system.users