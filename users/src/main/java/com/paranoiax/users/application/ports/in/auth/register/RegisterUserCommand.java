package com.paranoiax.users.application.ports.in.auth.register;

import com.paranoiax.core.application.OperationCommand;

public record RegisterUserCommand(
        String username,
        String inviteToken,
        String identityKey,
        DeviceInfo device,
        String operationId
) implements OperationCommand {
}