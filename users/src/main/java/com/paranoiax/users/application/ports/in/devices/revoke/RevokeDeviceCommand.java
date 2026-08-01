package com.paranoiax.users.application.ports.in.devices.revoke;

import java.util.UUID;

public record RevokeDeviceCommand(
        UUID userId,
        UUID deviceId
) {
}