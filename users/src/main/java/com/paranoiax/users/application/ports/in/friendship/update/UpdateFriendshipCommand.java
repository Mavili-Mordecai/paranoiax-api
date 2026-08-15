package com.paranoiax.users.application.ports.in.friendship.update;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record UpdateFriendshipCommand(
        UUID id,
        UUID userId,
        String attributes,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":", attributes, operationId);
    }
}