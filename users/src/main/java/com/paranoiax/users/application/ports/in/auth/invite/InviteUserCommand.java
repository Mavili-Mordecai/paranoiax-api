package com.paranoiax.users.application.ports.in.auth.invite;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record InviteUserCommand(
        UUID userId,
        String operationId
) implements OperationCommand {
    public static InviteUserCommand of(UUID userId, String operationId) {
        return new InviteUserCommand(userId, operationId);
    }
}
