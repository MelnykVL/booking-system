--liquibase formatted sql

--changeset MelnykVL:create-accommodation_type-enum
--comment create enum accommodation_type
CREATE TYPE ACCOMMODATION_TYPE AS ENUM('HOME', 'FLAT', 'APARTMENTS');
--rollback drop enum accommodation_type

--changeset MelnykVL:create-booking_system.units-table
--comment create table booking_system.units
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
) PARTITION BY list(type);
--rollback drop table booking_system.users

--changeset MelnykVL:create-index-idx_unit_catalog
--comment create index idx_unit_catalog
CREATE INDEX idx_unit_catalog ON booking_system.units(number_of_rooms, floor, type);
--rollback drop index idx_unit_catalog

--changeset MelnykVL:create-index-idx_unit_price
--comment create index idx_unit_price
CREATE INDEX idx_unit_price ON booking_system.units(price_per_night);
--rollback drop index idx_unit_price

--changeset MelnykVL:create-partition-booking_system.units-homes
--comment create partition homes for booking_system.units table
CREATE TABLE homes PARTITION OF booking_system.units
    FOR VALUES IN ('HOME');
--rollback drop partition homes

--changeset MelnykVL:create-partition-booking_system.units-flats
--comment create partition flats for booking_system.units table
CREATE TABLE flats PARTITION OF booking_system.units
    FOR VALUES IN ('FLAT');
--rollback drop partition flats

--changeset MelnykVL:create-partition-booking_system.units-apartments
--comment create partition apartments for booking_system.units table
CREATE TABLE apartments PARTITION OF booking_system.units
    FOR VALUES IN ('APARTMENTS');
--rollback drop partition apartments