--liquibase formatted sql

--changeset mavili:005-create-chats-invites
CREATE TABLE IF NOT EXISTS chats.chats_invites
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    chat_id       UUID NOT NULL REFERENCES chats.chats (id) ON DELETE CASCADE,
    created_by_id UUID NOT NULL,
    uses_count    INT  NOT NULL    DEFAULT 0,
    max_uses      INT,
    expires_at    TIMESTAMPTZ,
    CONSTRAINT chats_invites_uses_count_check CHECK (uses_count >= 0),
    CONSTRAINT chats_invites_max_uses_check CHECK (max_uses > 0),
    CONSTRAINT chats_invites_limit_check CHECK (max_uses IS NULL OR uses_count <= max_uses)
);
--rollback DROP TABLE IF EXISTS chats.chats_invites;