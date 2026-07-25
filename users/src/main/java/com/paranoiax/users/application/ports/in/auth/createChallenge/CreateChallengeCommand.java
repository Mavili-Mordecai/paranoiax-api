package com.paranoiax.users.application.ports.in.auth.createChallenge;

import java.util.UUID;

public record CreateChallengeCommand(
        UUID deviceId,
        String operationId
) {
}