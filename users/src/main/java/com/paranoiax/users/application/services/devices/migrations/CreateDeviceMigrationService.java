package com.paranoiax.users.application.services.devices.migrations;

import com.paranoiax.users.application.ports.in.devices.migrations.createMigration.CreateDeviceMigrationCommand;
import com.paranoiax.users.application.ports.in.devices.migrations.createMigration.CreateDeviceMigrationUseCase;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.MediaStoragePort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.EncryptionKey;
import com.paranoiax.users.domain.models.IdentityKey;
import com.paranoiax.users.domain.models.device.DeviceSignature;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.UserId;

import java.time.Duration;
import java.util.UUID;

public class CreateDeviceMigrationService implements CreateDeviceMigrationUseCase {
    private final MediaStoragePort mediaStoragePort;
    private final DeviceMigrationPort deviceMigrationPort;
    private final UserPort userPort;
    private final TokenGenerator tokenGenerator;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;
    private final int tokenSize;

    public CreateDeviceMigrationService(
            MediaStoragePort mediaStoragePort,
            DeviceMigrationPort deviceMigrationPort,
            UserPort userPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl,
            int tokenSize
    ) {
        this.mediaStoragePort = mediaStoragePort;
        this.userPort = userPort;
        this.deviceMigrationPort = deviceMigrationPort;
        this.tokenGenerator = tokenGenerator;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
        this.tokenSize = tokenSize;
    }

    @Override
    public String execute(CreateDeviceMigrationCommand command) {
        return executor.execute(command, String.class, lockTtl, resultTtl, () -> {
            User user = userPort.findById(new UserId(command.userId())).orElseThrow(() -> new NotFoundException("User"));

            DeviceMigration migration = deviceMigrationPort.insert(DeviceMigration.create(
                    new DeviceMigrationId(command.migrationId()),
                    user.getId(),
                    tokenGenerator.generate(tokenSize),
                    UUID.randomUUID(),
                    new IdentityKey(command.identityKey()),
                    new EncryptionKey(command.encryptionKey()),
                    new DeviceSignature(command.deviceSignature()),
                    resultTtl
            ), resultTtl);

            return mediaStoragePort.generateUploadUrl(migration.getBlobId(), resultTtl);
        });
    }
}