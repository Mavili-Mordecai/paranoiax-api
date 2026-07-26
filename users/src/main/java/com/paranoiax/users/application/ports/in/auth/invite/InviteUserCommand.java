package com.paranoiax.users.application.ports.in.auth.invite;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record InviteUserCommand(
        UUID userId,
        String operationId
) implements OperationCommand {
    public static InviteUserCommand of(UUID userId, String operationId) {
        return new InviteUserCommand(userId, operationId);
    }

    @Override
    public String getPayloadSignature() {
        return String.join(":", userId.toString(), operationId);
    }
}
