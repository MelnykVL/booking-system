--liquibase formatted sql

--changeset MelnykVL:create-units-table
--comment create table booking_system.units
CREATE TYPE ACCOMMODATION_TYPE AS ENUM('HOME', 'FLAT', 'APARTMENTS')

CREATE TABLE booking_system.units (
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT REFERENCES booking_system.users(id),
    number_of_rooms     INTEGER NOT NULL,
    type                ACCOMMODATION_TYPE NOT NULL,
    is_available        BOOLEAN DEFAULT false,
    base_cost           NUMERIC(10, 2) NOT NULL,
    description         TEXT,
    created_at          TIMESTAMPZ NOT NULL ,
    updated_at          TIMESTAMPZ NOT NULL
)
--rollback drop table booking_system.users