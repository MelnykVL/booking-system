--liquibase formatted sql

--changeset MelnykVL:create-events_log-table
--comment create new booking_system.events_log table
CREATE TABLE events_log (
    uuid            UUID PRIMARY KEY,
    entity          VARCHAR(100) NOT NULL,
    entity_id       BIGIN NOT NULL,
    action          TEXT NOT NULL,
    payload         JSONB NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL
);
--rollback drop table booking_system.events;

--changeset MelnykVL:create-index-idx_events_entity_id
--comment create index idx_events_entity_id for events_log.entity and events_log.entity_id columns
CREATE INDEX idx_events_entity_id
    ON events_log(entity, entity_id);
--rollback drop index idx_events_entity_id