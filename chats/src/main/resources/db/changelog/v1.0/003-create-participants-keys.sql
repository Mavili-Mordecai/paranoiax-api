--liquibase formatted sql

--changeset mavili:003-create-participants-keys
CREATE TABLE IF NOT EXISTS chats.chats_participants_keys
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    participant_id UUID NOT NULL REFERENCES chats.chats_participants (id) ON DELETE CASCADE,
    device_id      UUID NOT NULL,
    key_version    INT  NOT NULL,
    encryption_key TEXT NOT NULL,
    CONSTRAINT chats_participants_keys_key_version_check CHECK (key_version > 0)
);
--rollback DROP TABLE IF EXISTS chats.chats_participants_keys;