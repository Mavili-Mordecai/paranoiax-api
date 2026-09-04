package com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl;

import com.paranoiax.core.application.OperationCommand;

import java.util.UUID;

public record GenerateDeviceMigrationDownloadUrlCommand(
        UUID migrationId,
        String signature,
        String operationId
) implements OperationCommand {
}