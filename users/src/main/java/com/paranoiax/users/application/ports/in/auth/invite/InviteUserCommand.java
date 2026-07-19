package com.paranoiax.users.application.ports.in.auth.invite;

import com.paranoiax.users.domain.models.user.UserId;

public record InviteUserCommand(
        UserId userId,
        String operationId
) {
    public static InviteUserCommand of(UserId userId, String operationId) {
        return new InviteUserCommand(userId, operationId);
    }
}
