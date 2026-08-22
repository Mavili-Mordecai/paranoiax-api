--liquibase formatted sql

--changeset mavili:002-create-participants
CREATE TABLE IF NOT EXISTS chats.chats_participants
(
    id            UUID PRIMARY KEY       DEFAULT gen_random_uuid(),
    chat_id       UUID          NOT NULL REFERENCES chats.chats (id) ON DELETE CASCADE,
    user_id       UUID          NOT NULL,
    joined_at     TIMESTAMPTZ   NOT NULL,
    is_muted      BOOLEAN       NOT NULL DEFAULT FALSE,
    is_pinned     BOOLEAN       NOT NULL DEFAULT FALSE,
    last_read_seq INT           NOT NULL,
    role          VARCHAR(16)   NOT NULL DEFAULT 'MEMBER',
    permissions   VARCHAR(16)[] NOT NULL,
    CONSTRAINT chats_participants_role_check CHECK (role IN ('MEMBER', 'ADMIN', 'OWNER')),
    CONSTRAINT chats_participants_last_read_seq_check CHECK (last_read_seq >= 0),
    UNIQUE (chat_id, user_id)
);
--rollback DROP TABLE IF EXISTS chats.chats_participants;