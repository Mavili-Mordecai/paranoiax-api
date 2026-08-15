package com.paranoiax.users.application.ports.in.friendship.unblock;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record UnblockFriendshipCommand(
        UUID id,
        UUID userId,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                id.toString(),
                userId.toString(),
                operationId
        );
    }
}