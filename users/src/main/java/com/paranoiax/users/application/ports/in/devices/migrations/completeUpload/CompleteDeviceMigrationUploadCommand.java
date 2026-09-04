package com.paranoiax.users.application.ports.in.devices.migrations.completeUpload;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record CompleteDeviceMigrationUploadCommand(
        UUID migrationId,
        UUID userId,
        String operationId
) implements OperationCommand {
}