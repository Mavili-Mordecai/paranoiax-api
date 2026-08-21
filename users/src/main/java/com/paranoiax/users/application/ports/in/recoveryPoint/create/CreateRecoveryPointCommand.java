package com.paranoiax.users.application.ports.in.recoveryPoint.create;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record CreateRecoveryPointCommand(
        UUID userId,
        String identityKey,
        String encryptedData,
        String operationId
) implements OperationCommand {
}
