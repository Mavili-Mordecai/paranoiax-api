package com.paranoiax.users.application.ports.in.auth.register;

public record RegisterUserCommand(
        String username,
        String inviteToken,
        String identityKey,
        DeviceInfo device,
        String operationId
) {
}
