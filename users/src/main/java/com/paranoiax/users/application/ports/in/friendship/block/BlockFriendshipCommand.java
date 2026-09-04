package com.paranoiax.users.application.ports.in.friendship.block;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record BlockFriendshipCommand(
        UUID userId,
        UUID friendId,
        String operationId
) implements OperationCommand {
}