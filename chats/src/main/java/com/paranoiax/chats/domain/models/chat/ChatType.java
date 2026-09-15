package com.paranoiax.chats.domain.models.chat;

import com.paranoiax.chats.domain.models.participant.ParticipantPermission;
import com.paranoiax.chats.domain.models.participant.ParticipantRole;

import java.util.Map;
import java.util.Set;

public enum ChatType {
    SAVED(Map.of(ParticipantRole.MEMBER, Set.of(ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE))),
    PRIVATE(Map.of(ParticipantRole.MEMBER, Set.of(ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE))),
    ISOLATED(Map.of(ParticipantRole.MEMBER, Set.of(ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE))),
    GROUP(Map.of(
            ParticipantRole.MEMBER, Set.of(ParticipantPermission.ADD, ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE),
            ParticipantRole.ADMIN, Set.of(ParticipantPermission.ADD, ParticipantPermission.PIN, ParticipantPermission.SEND_MESSAGE, ParticipantPermission.CREATE_INVITE)
    )),
    CHANNEL(Map.of(
            ParticipantRole.MEMBER, Set.of(),
            ParticipantRole.ADMIN, Set.of(ParticipantPermission.ADD, ParticipantPermission.PIN, ParticipantPermission.CREATE_INVITE)
    ));

    private final Map<ParticipantRole, Set<ParticipantPermission>> defaultPermissions;

    ChatType(Map<ParticipantRole, Set<ParticipantPermission>> defaultPermissions) {
        this.defaultPermissions = defaultPermissions;
    }

    public Set<ParticipantPermission> getDefaultPermissions(ParticipantRole role) {
        return defaultPermissions.getOrDefault(role, Set.of());
    }
}
