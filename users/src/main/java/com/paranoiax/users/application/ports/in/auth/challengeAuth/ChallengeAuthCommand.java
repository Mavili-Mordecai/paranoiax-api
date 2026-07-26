package com.paranoiax.users.application.ports.in.auth.challengeAuth;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record ChallengeAuthCommand(
        UUID deviceId,
        String signature,
        String challenge,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                deviceId.toString(),
                signature,
                challenge,
                operationId
        );
    }
}