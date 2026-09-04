--liquibase formatted sql

--changeset mavili:004-create-groups-profiles
CREATE TABLE IF NOT EXISTS chats.chats_groups_profiles
(
    id         UUID PRIMARY KEY REFERENCES chats.chats (id) ON DELETE CASCADE,
    name       VARCHAR(128) NOT NULL,
    icon_small TEXT,
    icon_large TEXT,
    updated_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE IF EXISTS chats.chats_groups_profiles;