package com.paranoiax.users.application.ports.in.devices.migrations.completeUpload;

public interface CompleteDeviceMigrationUploadUseCase {
    void execute(CompleteDeviceMigrationUploadCommand command);
}