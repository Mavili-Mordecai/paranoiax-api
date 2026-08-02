package com.paranoiax.users.application.services.devices;

import com.paranoiax.core.domain.devices.DeviceType;
import com.paranoiax.users.application.ports.in.devices.register.RegisterDeviceCommand;
import com.paranoiax.users.application.ports.in.devices.register.RegisterDeviceUseCase;
import com.paranoiax.users.application.ports.out.DeviceMigrationPort;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.ports.out.crypto.SignatureVerifierPort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.core.domain.exceptions.InvalidSignatureException;
import com.paranoiax.core.domain.exceptions.InvalidValueException;
import com.paranoiax.core.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceName;
import com.paranoiax.users.domain.models.device.migration.DeviceMigration;
import com.paranoiax.users.domain.models.device.migration.DeviceMigrationId;

import java.time.Duration;

public class RegisterDeviceService implements RegisterDeviceUseCase {
    private final DeviceMigrationPort deviceMigrationPort;
    private final DevicePort devicePort;
    private final SignatureVerifierPort verifierPort;
    private final OperationExecutor operationExecutor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public RegisterDeviceService(
            DeviceMigrationPort deviceMigrationPort,
            DevicePort devicePort,
            SignatureVerifierPort verifierPort,
            OperationExecutor operationExecutor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.deviceMigrationPort = deviceMigrationPort;
        this.devicePort = devicePort;
        this.verifierPort = verifierPort;
        this.operationExecutor = operationExecutor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public void execute(RegisterDeviceCommand command) {
        operationExecutor.execute(command, Device.class, lockTtl, resultTtl, () -> {
            DeviceMigration migration = deviceMigrationPort.findById(new DeviceMigrationId(command.migrationId()))
                    .orElseThrow(() -> new NotFoundException("Device migration"));

            checkCommand(command, migration);
            migration.checkNotExpired();

            Device device = devicePort.insert(Device.create(
                    migration.getDeviceId(),
                    migration.getUserId(),
                    new DeviceName(command.deviceName()),
                    DeviceType.valueOf(command.deviceType()),
                    migration.getIdentityKey(),
                    migration.getEncryptionKey(),
                    migration.getDeviceSignature()
            ));

            deviceMigrationPort.deleteById(migration.getId());

            return device;
        });
    }

    private void checkCommand(RegisterDeviceCommand command, DeviceMigration migration) {
        if (!migration.getDeviceId().value().equals(command.deviceId())) {
            throw new InvalidValueException("deviceId");
        }

        if (!migration.getIdentityKey().value().equals(command.identityKey())) {
            throw new InvalidValueException("identityKey");
        }

        if (!migration.getEncryptionKey().value().equals(command.encryptionKey())) {
            throw new InvalidValueException("encryptionKey");
        }

        if (!migration.getDeviceSignature().value().equals(command.deviceSignature())) {
            throw new InvalidValueException("deviceSignature");
        }

        boolean verified = verifierPort.verify(
                migration.getIdentityKey().value(),
                migration.getChallenge().value(),
                command.signature()
        );
        if (!verified) {
            throw new InvalidSignatureException("Challenge");
        }
    }
}