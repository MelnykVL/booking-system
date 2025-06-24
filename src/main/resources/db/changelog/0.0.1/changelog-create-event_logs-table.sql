--liquibase formatted sql

--changeset MelnykVL:create-event_logs-table
--comment create new booking_system.event_logs table
CREATE TABLE event_logs (
    uuid            UUID PRIMARY KEY,
    entity          VARCHAR(100) NOT NULL,
    entity_id       BIGINT NOT NULL,
    action          TEXT NOT NULL,
    payload         JSONB NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL
);
--rollback drop table booking_system.events;

--changeset MelnykVL:create-index-idx_events_entity_id
--comment create index idx_events_entity_id for event_logs.entity and event_logs.entity_id columns
CREATE INDEX idx_events_entity_id
    ON event_logs(entity, entity_id);
--rollback drop index idx_events_entity_id