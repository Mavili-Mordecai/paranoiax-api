package com.paranoiax.users.application.services.devices.migrations;

import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.DeviceMigrationDownloadUrlResult;
import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.GenerateDeviceMigrationDownloadUrlCommand;
import com.paranoiax.users.application.ports.in.devices.migrations.generateDownloadUrl.GenerateDeviceMigrationDownloadUrlUseCase;
import com.paranoiax.users.application.ports.out.ChallengeVerifierPort;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.MediaStoragePort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.ExpiredException;
import com.paranoiax.users.domain.exceptions.InvalidSignatureException;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;

import java.time.Duration;
import java.util.Base64;

public class GenerateDeviceMigrationDownloadUrlService implements GenerateDeviceMigrationDownloadUrlUseCase {
    private final MediaStoragePort mediaStoragePort;
    private final DeviceMigrationPort deviceMigrationPort;
    private final UserPort userPort;
    private final ChallengeVerifierPort verifierPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public GenerateDeviceMigrationDownloadUrlService(
            MediaStoragePort mediaStoragePort,
            DeviceMigrationPort deviceMigrationPort,
            UserPort userPort,
            ChallengeVerifierPort verifierPort,
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

            try {
                boolean verified = verifierPort.verify(
                        Base64.getDecoder().decode(migration.getIdentityKey().value()),
                        Base64.getDecoder().decode(migration.getChallenge().value()),
                        Base64.getDecoder().decode(command.signature())
                );

                if (!verified) {
                    throw new InvalidSignatureException("Challenge");
                }
            } catch (IllegalArgumentException e) {
                throw new InvalidSignatureException("Challenge");
            }

            return new DeviceMigrationDownloadUrlResult(
                    mediaStoragePort.generateDownloadUrl(migration.getBlobId(), resultTtl),
                    migration.getDeviceSignature().value()
            );
        });
    }
}