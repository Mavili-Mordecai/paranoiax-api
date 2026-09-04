package com.paranoiax.users.application.services.devices;

import com.paranoiax.core.domain.devices.DeviceType;
import com.paranoiax.core.domain.exceptions.*;
import com.paranoiax.users.application.ports.in.devices.recover.RegisterRecoveredDeviceCommand;
import com.paranoiax.users.application.ports.in.devices.recover.RegisterRecoveredDeviceUseCase;
import com.paranoiax.users.application.ports.out.*;
import com.paranoiax.core.application.services.OperationExecutor;
import com.paranoiax.users.application.services.RecoveryChallengeValidator;
import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.IdentityKey;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceName;
import com.paranoiax.users.domain.models.device.DeviceSignature;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.Username;

import java.time.Duration;

public class RegisterRecoveredDeviceService implements RegisterRecoveredDeviceUseCase {
    private final UserPort userPort;
    private final ChallengePort challengePort;
    private final RecoveryPointPort recoveryPointPort;
    private final DevicePort devicePort;
    private final RecoveryChallengeValidator validator;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public RegisterRecoveredDeviceService(
            UserPort userPort,
            ChallengePort challengePort,
            RecoveryPointPort recoveryPointPort,
            DevicePort devicePort,
            RecoveryChallengeValidator validator,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.userPort = userPort;
        this.challengePort = challengePort;
        this.recoveryPointPort = recoveryPointPort;
        this.devicePort = devicePort;
        this.validator = validator;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public void execute(RegisterRecoveredDeviceCommand command) {
        executor.execute(command, Device.class, lockTtl, resultTtl, () -> {
            User user = userPort.findByUsername(new Username(command.username()))
                    .orElseThrow(() -> new NotFoundException("User"));

            Challenge challenge = consumeAndValidateChallenge(command, user);

            verifyRecoveryPoint(command.signature(), challenge);

            Device device = Device.create(
                    challenge.getDeviceId(),
                    user.getId(),
                    new DeviceName(command.deviceName()),
                    DeviceType.valueOf(command.deviceType()),
                    new IdentityKey(command.identityKey()),
                    new EncryptionKey(command.encryptionKey()),
                    new DeviceSignature(command.deviceSignature())
            );

            validator.validateDeviceSignature(
                    user.getIdentityKey(),
                    device
            );

            return devicePort.insert(device);
        });
    }

    private Challenge consumeAndValidateChallenge(RegisterRecoveredDeviceCommand command, User user) {
        Challenge challenge = challengePort.consume(command.challenge())
                .orElseThrow(() -> new NotFoundException("Challenge"));

        if (!challenge.getUserId().equals(user.getId())) {
            throw new AccessDeniedException();
        }

        validator.validateChallenge(command.deviceId(), challenge);

        return challenge;
    }

    private void verifyRecoveryPoint(String signature, Challenge challenge) {
        RecoveryPoint recoveryPoint = recoveryPointPort.find(challenge.getUserId())
                .orElseThrow(() -> new NotFoundException("RecoveryPoint"));

        validator.validateRecoveryPoint(signature, recoveryPoint, challenge);
    }
}