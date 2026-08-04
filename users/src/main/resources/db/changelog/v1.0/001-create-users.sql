--liquibase formatted sql

--changeset mavili:001-create-users
CREATE TABLE IF NOT EXISTS users.users
(
    id              UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    identity_key    TEXT        NOT NULL UNIQUE,
    username        VARCHAR(32) NOT NULL UNIQUE,
    type            VARCHAR(4)  NOT NULL DEFAULT 'USER',
    profile         TEXT        NULL     DEFAULT NULL,
    profile_version INT         NOT NULL DEFAULT 0,
    invited_by_id   UUID REFERENCES users.users (id),
    last_seen_at    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revoked_at      TIMESTAMPTZ NULL     DEFAULT NULL,
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_type_check CHECK (type IN ('USER', 'BOT'))
);
--rollback DROP TABLE users;
