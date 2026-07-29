package com.paranoiax.users.application.ports.in.devices.migrations.createMigration;

public interface CreateDeviceMigrationUseCase {
    String execute(CreateDeviceMigrationCommand command);
}