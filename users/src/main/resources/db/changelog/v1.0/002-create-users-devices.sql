--liquibase formatted sql

--changeset mavili:002-create-users-devices
CREATE TABLE IF NOT EXISTS users.users_devices
(
    id               UUID PRIMARY KEY,
    user_id          UUID        NOT NULL REFERENCES users.users (id),
    name             VARCHAR(64) NOT NULL,
    type             VARCHAR(16) NOT NULL,
    identity_key     TEXT        NOT NULL UNIQUE,
    encryption_key   TEXT        NOT NULL UNIQUE,
    device_signature TEXT        NOT NULL UNIQUE,
    last_seen_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_devices_type_check CHECK (type IN ('MOBILE', 'DESKTOP'))
);
--rollback DROP TABLE IF EXISTS users.users_devices;