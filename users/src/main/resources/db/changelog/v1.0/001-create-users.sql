--liquibase formatted sql

--changeset mavili:001-create-users
CREATE TABLE users.users
(
    id                UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    identity_key      TEXT        NOT NULL UNIQUE,
    username          VARCHAR(32) NOT NULL UNIQUE,
    type              VARCHAR(4)  NOT NULL DEFAULT 'USER',
    first_name        VARCHAR(64),
    last_name         VARCHAR(64),
    bio               VARCHAR(128),
    invited_by_id     UUID REFERENCES users.users (id),
    last_seen_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE users.users ADD CONSTRAINT users_type_check CHECK (type IN ('USER', 'BOT'));
--rollback DROP TABLE users;
