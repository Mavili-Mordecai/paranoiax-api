package com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record GenerateDeviceMigrationDownloadUrlCommand(
        UUID migrationId,
        String signature,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":", migrationId.toString(), signature, operationId);
    }
}