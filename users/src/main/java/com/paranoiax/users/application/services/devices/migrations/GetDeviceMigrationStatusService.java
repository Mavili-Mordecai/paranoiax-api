package com.paranoiax.users.application.services.devices.migrations;

import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.DeviceMigrationStatusResult;
import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.GetDeviceMigrationStatusUseCase;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;

import java.util.UUID;

public class GetDeviceMigrationStatusService implements GetDeviceMigrationStatusUseCase {
    private final DeviceMigrationPort port;

    public GetDeviceMigrationStatusService(DeviceMigrationPort port) {
        this.port = port;
    }

    @Override
    public DeviceMigrationStatusResult execute(UUID migrationId) {
        DeviceMigration migration = port.findById(new DeviceMigrationId(migrationId))
                .orElseThrow(() -> new NotFoundException("Migration"));

        return new DeviceMigrationStatusResult(migration.getStatus(), migration.getChallenge());
    }
}