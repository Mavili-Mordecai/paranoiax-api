--liquibase formatted sql

--changeset mavili:004-create-friendships-keys
CREATE TABLE IF NOT EXISTS users.friendships_keys
(
    id               UUID PRIMARY KEY,
    friendship_id    UUID        NOT NULL REFERENCES users.friendships (id),
    friend_device_id UUID        NOT NULL REFERENCES users.users_devices (id),
    shared_key       TEXT        NOT NULL,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (friendship_id, friend_device_id)
);
--rollback DROP TABLE IF EXISTS users.friendships_keys;