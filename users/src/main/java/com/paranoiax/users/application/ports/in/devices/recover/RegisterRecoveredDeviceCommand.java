package com.paranoiax.users.application.ports.in.devices.recover;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

import java.util.UUID;

public record RegisterRecoveredDeviceCommand(
        UUID deviceId,
        String challenge,
        String signature,
        String username,
        String deviceName,
        String deviceType,
        String identityKey,
        String encryptionKey,
        String deviceSignature,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":",
                deviceId.toString(),
                challenge,
                signature,
                username,
                deviceName,
                deviceType,
                identityKey,
                encryptionKey,
                deviceSignature,
                operationId
        );
    }
}
