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

--changeset MelnykVL:create-index-idx_unit_catalog
--comment create index idx_unit_catalog
CREATE INDEX idx_unit_catalog ON booking_system.units(number_of_rooms, floor, type);
--rollback drop index idx_unit_catalog

--changeset MelnykVL:create-index-idx_unit_price
--comment create index idx_unit_price
CREATE INDEX idx_unit_price ON booking_system.units(price_per_night);
--rollback drop index idx_unit_price