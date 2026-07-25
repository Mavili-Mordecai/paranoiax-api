package com.paranoiax.users.application.ports.in.auth.challengeAuth;

import java.util.UUID;

public record ChallengeAuthCommand(
        UUID deviceId,
        String signature,
        String challenge,
        String operationId
) {
}