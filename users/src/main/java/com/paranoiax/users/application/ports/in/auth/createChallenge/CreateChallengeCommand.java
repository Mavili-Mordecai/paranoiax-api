package com.paranoiax.users.application.ports.in.auth.createChallenge;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;
import com.paranoiax.users.domain.models.challenge.ChallengeType;

import java.util.UUID;

public record CreateChallengeCommand(
        UUID deviceId,
        ChallengeType challengeType,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                deviceId.toString(),
                challengeType.toString(),
                operationId
        );
    }
}