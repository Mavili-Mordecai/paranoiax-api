package com.paranoiax.users.application.ports.in.auth.createChallenge;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record CreateChallengeCommand(
        UUID deviceId,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                deviceId.toString(),
                operationId
        );
    }
}