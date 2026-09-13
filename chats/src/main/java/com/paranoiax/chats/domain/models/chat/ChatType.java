package com.paranoiax.chats.domain.models.chat;

import com.paranoiax.chats.domain.models.participant.ParticipantPermission;

import java.util.Set;

public enum ChatType {
    SAVED(Set.of(ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE)),
    PRIVATE(Set.of(ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE)),
    ISOLATED(Set.of(ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE)),
    GROUP(Set.of(ParticipantPermission.ADD, ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE)),
    CHANNEL(Set.of());

    private final Set<ParticipantPermission> defaultPermissions;

    ChatType(Set<ParticipantPermission> defaultPermissions) {
        this.defaultPermissions = defaultPermissions;
    }

    public Set<ParticipantPermission> getDefaultPermissions() {
        return defaultPermissions;
    }
}
