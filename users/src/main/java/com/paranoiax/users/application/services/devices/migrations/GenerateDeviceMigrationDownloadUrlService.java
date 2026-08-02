package com.paranoiax.users.application.services.devices.migrations;

import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.DeviceMigrationDownloadUrlResult;
import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.GenerateDeviceMigrationDownloadUrlCommand;
import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.GenerateDeviceMigrationDownloadUrlUseCase;
import com.paranoiax.users.application.ports.out.crypto.SignatureVerifierPort;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.MediaStoragePort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.ExpiredException;
import com.paranoiax.users.domain.exceptions.InvalidSignatureException;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationStatus;

import java.time.Duration;

public class GenerateDeviceMigrationDownloadUrlService implements GenerateDeviceMigrationDownloadUrlUseCase {
    private final MediaStoragePort mediaStoragePort;
    private final DeviceMigrationPort deviceMigrationPort;
    private final UserPort userPort;
    private final SignatureVerifierPort verifierPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public GenerateDeviceMigrationDownloadUrlService(
            MediaStoragePort mediaStoragePort,
            DeviceMigrationPort deviceMigrationPort,
            UserPort userPort,
            SignatureVerifierPort verifierPort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.mediaStoragePort = mediaStoragePort;
        this.deviceMigrationPort = deviceMigrationPort;
        this.userPort = userPort;
        this.verifierPort = verifierPort;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public DeviceMigrationDownloadUrlResult execute(GenerateDeviceMigrationDownloadUrlCommand command) {
        return executor.execute(command, DeviceMigrationDownloadUrlResult.class, lockTtl, resultTtl, () -> {
            DeviceMigration migration = deviceMigrationPort.findById(new DeviceMigrationId(command.migrationId()))
                    .orElseThrow(() -> new NotFoundException("Device migration"));

            userPort.findById(migration.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));

            if (migration.isExpired()) {
                throw new ExpiredException("Device migration");
            }

            if (migration.getStatus() != DeviceMigrationStatus.READY_FOR_AUTH) {
                throw new IllegalStateException("Device migration is not ready for registration");
            }

            boolean verified = verifierPort.verify(
                    migration.getIdentityKey().value(),
                    migration.getChallenge().value(),
                    command.signature()
            );

            if (!verified) {
                throw new InvalidSignatureException("Challenge");
            }

            return new DeviceMigrationDownloadUrlResult(
                    mediaStoragePort.generateDownloadUrl(migration.getBlobId(), resultTtl),
                    migration.getDeviceSignature().value()
            );
        });
    }
}