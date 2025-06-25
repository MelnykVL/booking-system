--liquibase formatted sql

--changeset MelnykVL:create-extension-btree_gist
--comment create extension btree_gist
CREATE EXTENSION IF NOT EXISTS btree_gist;
--rollback drop extension btree_gist