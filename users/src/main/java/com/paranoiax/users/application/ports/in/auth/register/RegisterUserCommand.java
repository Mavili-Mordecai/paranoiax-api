package com.paranoiax.users.application.ports.in.auth.register;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

public record RegisterUserCommand(
        String username,
        String inviteToken,
        String identityKey,
        DeviceInfo device,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":", username, inviteToken, identityKey, device.getOperationSignature(), operationId);
    }
}