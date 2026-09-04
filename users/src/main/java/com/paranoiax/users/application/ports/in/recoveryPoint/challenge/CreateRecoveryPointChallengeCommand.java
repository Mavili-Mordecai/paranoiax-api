package com.paranoiax.users.application.ports.in.recoveryPoint.challenge;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record CreateRecoveryPointChallengeCommand(
        UUID deviceId,
        String username,
        String operationId
) implements OperationCommand {
}
