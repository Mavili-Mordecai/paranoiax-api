--liquibase formatted sql

--changeset mavili:002-create-users-avatars
CREATE TABLE users_avatars
(
    id         UUID PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    small      TEXT        NOT NULL,
    medium     TEXT        NOT NULL,
    large      TEXT        NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE users_avatars;