--liquibase formatted sql

--changeset MelnykVL:create-booking_system-schema
--comment create schema booking_system
CREATE SCHEMA booking_system;
--rollback drop schema booking_system;