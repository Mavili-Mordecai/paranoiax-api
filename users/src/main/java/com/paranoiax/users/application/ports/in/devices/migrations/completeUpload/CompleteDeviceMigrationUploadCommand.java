package com.paranoiax.users.application.ports.in.devices.migrations.completeUpload;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record CompleteDeviceMigrationUploadCommand(
        UUID migrationId,
        UUID userId,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":", migrationId.toString(), userId.toString(), operationId);
    }
}