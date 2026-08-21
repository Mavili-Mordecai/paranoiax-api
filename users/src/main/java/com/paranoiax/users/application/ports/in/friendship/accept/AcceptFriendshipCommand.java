package com.paranoiax.users.application.ports.in.friendship.accept;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record AcceptFriendshipCommand(
        UUID id,
        UUID userId,
        String operationId
) implements OperationCommand {
}