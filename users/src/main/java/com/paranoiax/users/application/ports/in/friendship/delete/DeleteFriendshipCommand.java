package com.paranoiax.users.application.ports.in.friendship.delete;

import java.util.UUID;

public record DeleteFriendshipCommand(
        UUID id,
        UUID userId
) {
}