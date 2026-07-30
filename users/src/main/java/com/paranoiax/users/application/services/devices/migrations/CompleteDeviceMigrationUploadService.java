package com.paranoiax.users.application.services.devices.migrations;

import com.paranoiax.users.application.ports.in.devices.migrations.completeUpload.CompleteDeviceMigrationUploadCommand;
import com.paranoiax.users.application.ports.in.devices.migrations.completeUpload.CompleteDeviceMigrationUploadUseCase;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.AccessDeniedException;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;

import java.time.Duration;

public class CompleteDeviceMigrationUploadService implements CompleteDeviceMigrationUploadUseCase {
    private final DeviceMigrationPort deviceMigrationPort;
    private final TokenGenerator tokenGenerator;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;
    private final int size = 32;

    public CompleteDeviceMigrationUploadService(
            DeviceMigrationPort deviceMigrationPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.deviceMigrationPort = deviceMigrationPort;
        this.tokenGenerator = tokenGenerator;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public void execute(CompleteDeviceMigrationUploadCommand command) {
        executor.execute(command, DeviceMigration.class, lockTtl, resultTtl, () -> {
            DeviceMigration migration = deviceMigrationPort.findById(new DeviceMigrationId(command.migrationId()))
                    .orElseThrow(() -> new NotFoundException("Device migration"));

            if (!migration.getUserId().value().equals(command.userId())) {
                throw new AccessDeniedException();
            }

            migration.confirmUpload(new ChallengeValue(tokenGenerator.generate(size)));

            return deviceMigrationPort.update(migration, migration.getRemainingTtl());
        });
    }
}