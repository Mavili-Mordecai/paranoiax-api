package com.paranoiax.users.application.ports.in.devices.migrations.getMigrationStatus;

public interface GetDeviceMigrationStatusUseCase {
    DeviceMigrationStatusResult execute(GetDeviceMigrationStatusCommand command);
}
