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

--changeset MelnykVL:create-index-idx_event_logs_entity_entity_id
--comment create index idx_event_logs_entity_entity_id for event_logs.entity and event_logs.entity_id columns
CREATE INDEX idx_event_logs_entity_entity_id
    ON booking_system.event_logs(entity, entity_id);
--rollback drop index idx_event_logs_entity_entity_id