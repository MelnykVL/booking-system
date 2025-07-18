--liquibase formatted sql

--changeset MelnykVL:create-users-table
--comment create table booking_system.users
CREATE TABLE booking_system.users (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name      VARCHAR(250) NOT NULL,
    last_name       VARCHAR(250) NOT NULL,
    email           VARCHAR(250) UNIQUE NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL
);
--rollback drop table booking_system.users