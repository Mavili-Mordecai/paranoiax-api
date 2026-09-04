package com.paranoiax.users.application.ports.in.friendship.accept;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record AcceptFriendshipCommand(
        UUID id,
        UUID userId,
        String operationId
) implements OperationCommand {
}