package com.paranoiax.users.application.ports.in.devices.register;

import com.paranoiax.core.application.OperationCommand;

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
}