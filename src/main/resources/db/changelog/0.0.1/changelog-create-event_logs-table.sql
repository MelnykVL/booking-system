--liquibase formatted sql

--changeset MelnykVL:create-event_logs-table
--comment create new booking_system.event_logs table
CREATE TABLE booking_system.event_logs (
    uuid            UUID PRIMARY KEY,
    entity          VARCHAR(100) NOT NULL,
    entity_id       BIGINT,
    action          TEXT NOT NULL,
    payload         JSONB,
    created_at      TIMESTAMPTZ NOT NULL
);
--rollback drop table booking_system.events;

--changeset MelnykVL:create-index-idx_created
--comment create index idx_created
CREATE INDEX idx_created ON booking_system.event_logs(created_at DESC);
--rollback drop index idx_created