package com.paranoiax.users.application.ports.in.friendship.update;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record UpdateFriendshipCommand(
        UUID id,
        UUID userId,
        String attributes,
        String operationId
) implements OperationCommand {
}