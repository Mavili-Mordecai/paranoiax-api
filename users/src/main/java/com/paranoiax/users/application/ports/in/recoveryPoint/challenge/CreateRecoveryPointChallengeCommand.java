package com.paranoiax.users.application.ports.in.recoveryPoint.challenge;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record CreateRecoveryPointChallengeCommand(
        UUID deviceId,
        String username,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                deviceId.toString(),
                username,
                operationId
        );
    }
}
