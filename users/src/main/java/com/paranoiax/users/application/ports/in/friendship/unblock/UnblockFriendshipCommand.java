package com.paranoiax.users.application.ports.in.friendship.unblock;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record UnblockFriendshipCommand(
        UUID id,
        UUID userId,
        String operationId
) implements OperationCommand {
}