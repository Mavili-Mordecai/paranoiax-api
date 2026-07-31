package com.paranoiax.users.application.ports.in.devices.register;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record RegisterDeviceCommand(
        UUID migrationId,
        UUID deviceId,
        String deviceName,
        String deviceType,
        String signature,
        String identityKey,
        String encryptionKey,
        String deviceSignature,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                migrationId.toString(),
                deviceId.toString(),
                deviceName,
                deviceType,
                signature,
                identityKey,
                encryptionKey,
                deviceSignature,
                operationId
        );
    }
}