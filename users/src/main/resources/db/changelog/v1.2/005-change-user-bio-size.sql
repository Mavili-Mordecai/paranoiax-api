--liquibase formatted sql

--changeset mavili:005-change-user-bio-size
ALTER TABLE users.users ALTER COLUMN bio TYPE VARCHAR(192);
--rollback ALTER TABLE users.users ALTER COLUMN bio TYPE VARCHAR(128);