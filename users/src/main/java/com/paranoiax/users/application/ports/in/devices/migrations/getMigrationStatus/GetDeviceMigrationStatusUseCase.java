package com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus;

import java.util.UUID;

public interface GetDeviceMigrationStatusUseCase {
    DeviceMigrationStatusResult execute(UUID migrationId);
}
