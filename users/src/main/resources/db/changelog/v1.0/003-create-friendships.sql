--liquibase formatted sql

--changeset mavili:003-create-friendships
CREATE TABLE IF NOT EXISTS users.friendships
(
    id         UUID PRIMARY KEY,
    user_id    UUID        NOT NULL REFERENCES users.users (id),
    friend_id  UUID        NOT NULL REFERENCES users.users (id),
    status     VARCHAR(16) NOT NULL,
    attributes TEXT        NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT friendships_status_check CHECK (status IN ('INCOME', 'OUTCOME', 'ACCEPTED', 'BLOCKED')),
    UNIQUE (user_id, friend_id)
)
--rollback DROP TABLE IF EXISTS users.friendships;