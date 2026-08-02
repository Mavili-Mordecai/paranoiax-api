package com.paranoiax.users.application.services.devices.migrations;

import com.paranoiax.users.application.ports.in.devices.migrations.completeUpload.CompleteDeviceMigrationUploadCommand;
import com.paranoiax.users.application.ports.in.devices.migrations.completeUpload.CompleteDeviceMigrationUploadUseCase;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.EventPublisher;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.core.domain.exceptions.AccessDeniedException;
import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationUpdatedEvent;

import java.time.Duration;
import java.time.Instant;

public class CompleteDeviceMigrationUploadService implements CompleteDeviceMigrationUploadUseCase {
    private final DeviceMigrationPort deviceMigrationPort;
    private final TokenGenerator tokenGenerator;
    private final EventPublisher eventPublisher;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;
    private final int tokenSize;

    public CompleteDeviceMigrationUploadService(
            DeviceMigrationPort deviceMigrationPort,
            TokenGenerator tokenGenerator,
            EventPublisher eventPublisher,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl,
            int tokenSize
    ) {
        this.deviceMigrationPort = deviceMigrationPort;
        this.tokenGenerator = tokenGenerator;
        this.eventPublisher = eventPublisher;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
        this.tokenSize = tokenSize;
    }

    @Override
    public void execute(CompleteDeviceMigrationUploadCommand command) {
        executor.execute(command, DeviceMigration.class, lockTtl, resultTtl, () -> {
            DeviceMigration migration = deviceMigrationPort.findById(new DeviceMigrationId(command.migrationId()))
                    .orElseThrow(() -> new NotFoundException("Device migration"));

            if (!migration.getUserId().value().equals(command.userId())) {
                throw new AccessDeniedException();
            }

            migration.confirmUpload(new ChallengeValue(tokenGenerator.generate(tokenSize)));

            migration = deviceMigrationPort.update(migration, migration.getRemainingTtl());

            eventPublisher.publish(DeviceMigrationUpdatedEvent.from(migration, Instant.now()));

            return migration;
        });
    }
}