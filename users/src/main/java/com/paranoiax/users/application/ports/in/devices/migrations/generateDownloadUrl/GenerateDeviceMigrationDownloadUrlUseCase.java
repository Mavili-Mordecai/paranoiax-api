package com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl;

public interface GenerateDeviceMigrationDownloadUrlUseCase {
    DeviceMigrationDownloadUrlResult execute(GenerateDeviceMigrationDownloadUrlCommand command);
}