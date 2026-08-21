--liquibase formatted sql

--changeset mavili:005-create-users-recovery-points
CREATE TABLE IF NOT EXISTS users.users_recovery_points
(
    id             UUID PRIMARY KEY,
    user_id        UUID        NOT NULL REFERENCES users.users (id),
    identity_key   TEXT        NOT NULL UNIQUE,
    encrypted_data TEXT        NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE IF EXISTS users.users_recovery_points;

ALTER TABLE users.users_recovery_points ADD CONSTRAINT unique_user_id UNIQUE (user_id);
--rollback ALTER TABLE users.users_recovery_points DROP CONSTRAINT unique_user_id;