--liquibase formatted sql

--changeset mavili:003-create-users-devices
CREATE TABLE users_devices
(
    id               UUID PRIMARY KEY,
    user_id          UUID        NOT NULL REFERENCES users (id),
    name             VARCHAR(64) NOT NULL,
    type             VARCHAR(16) NOT NULL,
    identity_key     TEXT        NOT NULL,
    encryption_key   TEXT        NOT NULL,
    device_signature TEXT        NOT NULL,
    last_seen_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE users_devices
    ADD CONSTRAINT users_devices_type_check CHECK (type IN ('MOBILE', 'DESKTOP'));
--rollback DROP TABLE users_devices;