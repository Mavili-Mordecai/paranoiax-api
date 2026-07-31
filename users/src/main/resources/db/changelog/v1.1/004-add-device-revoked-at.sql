--liquibase formatted sql

--changeset mavili:004-add-device-revoke-at
ALTER TABLE users.users_devices
    ADD COLUMN IF NOT EXISTS revoked_at TIMESTAMP NULL DEFAULT NULL;
--rollback ALTER TABLE users.users_devices DROP COLUMN revoke_at;