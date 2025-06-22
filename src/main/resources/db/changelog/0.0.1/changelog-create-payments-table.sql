--liquibase formatted sql

--changeset MelnykVL:create-payments-table
--comment create table booking_system.payments
CREATE TYPE PAYMENT_STATUS AS ENUM('SUCCESS', 'FAILED', 'REFUNDED')

CREATE TABLE booking_system.payments (
    id                  BIGSERIAL PRIMARY KEY,
    booking_id          BIGINT REFERENCES booking_system.bookings(id),
    user_id             BIGINT REFERENCES booking_system.users(id),
    amount              NUMERIC(10, 2) NOT NULL,
    status              BOOKING_STATUS NOT NULL,
    created_at          TIMESTAMPZ NOT NULL
)
--rollback drop table booking_system.payments