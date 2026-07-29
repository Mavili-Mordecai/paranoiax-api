package com.paranoiax.users.application.ports.in.devices.migrations.createUploadUrl;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record CreateUploadUrlCommand(
        UUID userId,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                userId.toString(),
                operationId
        );
    }
}