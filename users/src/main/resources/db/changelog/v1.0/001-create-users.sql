--liquibase formatted sql

--changeset mavili:001-create-users
CREATE TABLE users
(
    id                UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    identity_key      TEXT        NOT NULL,
    username          VARCHAR(32) NOT NULL UNIQUE,
    first_name        VARCHAR(64),
    last_name         VARCHAR(64),
    bio               VARCHAR(128),
    invited_by        UUID REFERENCES users (id),
    last_seen_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at        TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE users;
