package com.paranoiax.users.application.ports.in.auth.register;

import java.util.UUID;

public record DeviceInfo(
        UUID id,
        String name,
        String type,
        String identityKey,
        String encryptionKey,
        String deviceSignature
) {
}
