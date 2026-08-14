package com.paranoiax.users.application.ports.in.friendship.deleteKeys;

import java.util.List;
import java.util.UUID;

public record DeleteFriendshipKeysCommand(
        UUID deviceId,
        List<UUID> ids
) {
}