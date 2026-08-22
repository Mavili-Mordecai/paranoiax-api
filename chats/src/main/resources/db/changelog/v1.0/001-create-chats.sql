--liquibase formatted sql

--changeset mavili:001-create-chats
CREATE TABLE IF NOT EXISTS chats.chats
(
    id               UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    type             VARCHAR(16) NOT NULL,
    events_seq       INT         NOT NULL DEFAULT 0,
    last_activity_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at       timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chats_type_check CHECK (type IN ('SAVED', 'PRIVATE', 'ISOLATED', 'GROUP', 'CHANNEL')),
    CONSTRAINT chats_events_seq_check CHECK (events_seq >= 0)
);
--rollback DROP TABLE IF EXISTS chats.chats;