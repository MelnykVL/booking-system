--liquibase formatted sql

--changeset MelnykVL:create-booking_system-schema
--comment create new booking_system schema
CREATE SCHEMA booking_system;
--rollback drop schema booking_system;