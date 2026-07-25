package com.paranoiax.users.application.ports.in.auth.invite;

import java.util.UUID;

public record InviteUserCommand(
        UUID userId,
        String operationId
) {
    public static InviteUserCommand of(UUID userId, String operationId) {
        return new InviteUserCommand(userId, operationId);
    }
}
