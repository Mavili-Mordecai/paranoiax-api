package com.paranoiax.users.application.ports.in.friendship.block;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record BlockFriendshipCommand(
        UUID userId,
        UUID friendId,
        String operationId
) implements OperationCommand {
}