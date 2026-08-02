package com.paranoiax.users.application.services.devices.migrations;

import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.DeviceMigrationStatusResult;
import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.GetDeviceMigrationStatusCommand;
import com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus.GetDeviceMigrationStatusUseCase;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.rateLimiter.RateLimiter;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;

import java.time.Duration;

public class GetDeviceMigrationStatusService implements GetDeviceMigrationStatusUseCase {
    private final DeviceMigrationPort port;
    private final RateLimiter rateLimiter;

    public GetDeviceMigrationStatusService(DeviceMigrationPort port, RateLimiter rateLimiter) {
        this.port = port;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public DeviceMigrationStatusResult execute(GetDeviceMigrationStatusCommand command) {
        rateLimiter.consume("get-device-migration-status:" + command.rateLimitKey(), Duration.ofSeconds(3));

        DeviceMigration migration = port.findById(new DeviceMigrationId(command.migrationId()))
                .orElseThrow(() -> new NotFoundException("Migration"));

        return new DeviceMigrationStatusResult(migration.getStatus(), migration.getChallenge());
    }
}