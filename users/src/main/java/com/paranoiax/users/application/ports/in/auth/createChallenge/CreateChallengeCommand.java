package com.paranoiax.users.application.ports.in.auth.createChallenge;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record CreateChallengeCommand(
        UUID deviceId,
        String operationId
) implements OperationCommand {
}