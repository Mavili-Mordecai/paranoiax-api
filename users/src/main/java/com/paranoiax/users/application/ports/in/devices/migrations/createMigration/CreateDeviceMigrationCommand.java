package com.paranoiax.users.application.ports.in.devices.migrations.createMigration;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record CreateDeviceMigrationCommand(
        UUID migrationId,
        UUID userId,
        String identityKey,
        String encryptionKey,
        String deviceSignature,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                migrationId.toString(),
                userId.toString(),
                identityKey,
                encryptionKey,
                deviceSignature,
                operationId
        );
    }
}