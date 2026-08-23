package com.paranoiax.chats.domain.models.groupProfile;

import com.paranoiax.chats.domain.models.chat.ChatId;
import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

import java.time.Instant;
import java.util.Objects;

public class GroupProfile {
    private final GroupProfileId id;
    private GroupProfileName name;
    private GroupProfileAvatar avatar;
    private Instant updatedAt;

    private GroupProfile(GroupProfileId id, GroupProfileName name, GroupProfileAvatar avatar, Instant updatedAt) {
        this.id = Require.notNull(id, DomainErrorCode.MISSING_REQUIRED_FIELD, "groupProfileId");
        this.name = Require.notNull(name, DomainErrorCode.MISSING_REQUIRED_FIELD, "name");
        this.updatedAt = Require.notNull(updatedAt, DomainErrorCode.MISSING_REQUIRED_FIELD, "updatedAt");
        this.avatar = avatar;
    }

    public static GroupProfile of(GroupProfileId id, GroupProfileName name, GroupProfileAvatar avatar, Instant updatedAt) {
        return new GroupProfile(id, name, avatar, updatedAt);
    }

    public static GroupProfile create(ChatId chatId, GroupProfileName name, GroupProfileAvatar avatar) {
        return new GroupProfile(GroupProfileId.from(chatId), name, avatar, Instant.now());
    }

    public void rename(GroupProfileName name) {
        if (Objects.equals(this.name.value(), name.value())) {
            return;
        }

        this.name = Require.notNull(name, DomainErrorCode.MISSING_REQUIRED_FIELD, "name");
        this.updatedAt = Instant.now();
    }

    public void changeAvatar(GroupProfileAvatar avatar) {
        Require.notNull(avatar, DomainErrorCode.MISSING_REQUIRED_FIELD, "avatar");

        if (Objects.equals(this.avatar, avatar)) {
            return;
        }

        this.avatar = avatar;
        this.updatedAt = Instant.now();
    }

    public GroupProfileAvatar getAvatar() {
        return avatar;
    }

    public GroupProfileName getName() {
        return name;
    }

    public GroupProfileId getId() {
        return id;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
