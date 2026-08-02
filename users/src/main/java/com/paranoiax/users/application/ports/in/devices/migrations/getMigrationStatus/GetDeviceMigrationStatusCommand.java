package com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus;

import java.util.UUID;

public record GetDeviceMigrationStatusCommand(
        UUID migrationId,
        String rateLimitKey
) {
}