package com.paranoiax.users.application.ports.in.profile.getKeys;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.domain.models.EncryptionKey;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceSignature;

import java.time.Instant;

public record UserDeviceInfo(
        DeviceId deviceId,
        IdentityKey identityKey,
        EncryptionKey encryptionKey,
        DeviceSignature deviceSignature,
        Instant revokedAt
) {
    public static UserDeviceInfo from(Device device) {
        return new UserDeviceInfo(
                device.getId(),
                device.getIdentityKey(),
                device.getEncryptionKey(),
                device.getDeviceSignature(),
                device.getRevokedAt()
        );
    }
}