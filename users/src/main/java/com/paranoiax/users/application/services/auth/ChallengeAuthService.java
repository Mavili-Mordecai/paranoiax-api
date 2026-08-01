package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.TokenPair;
import com.paranoiax.users.application.ports.in.auth.challengeAuth.ChallengeAuthCommand;
import com.paranoiax.users.application.ports.in.auth.challengeAuth.ChallengeAuthUseCase;
import com.paranoiax.users.application.ports.out.AuthTokenPort;
import com.paranoiax.users.application.ports.out.ChallengePort;
import com.paranoiax.users.application.ports.out.SignatureVerifierPort;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.ExpiredException;
import com.paranoiax.users.domain.exceptions.InvalidSignatureException;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceId;

import java.time.Duration;

public class ChallengeAuthService implements ChallengeAuthUseCase {
    private final DevicePort devicePort;
    private final ChallengePort challengePort;
    private final SignatureVerifierPort verifierPort;
    private final AuthTokenPort authTokenPort;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTtl;

    public ChallengeAuthService(
            DevicePort devicePort,
            ChallengePort challengePort,
            SignatureVerifierPort verifierPort,
            AuthTokenPort authTokenPort,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTtl
    ) {
        this.devicePort = devicePort;
        this.challengePort = challengePort;
        this.verifierPort = verifierPort;
        this.authTokenPort = authTokenPort;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTtl = resultTtl;
    }

    @Override
    public TokenPair execute(ChallengeAuthCommand command) {
        return executor.execute(command, TokenPair.class, lockTtl, resultTtl, () -> {
            Challenge challenge = challengePort.find(command.challenge()).orElseThrow(() -> new NotFoundException("Challenge"));
            challengePort.delete(challenge);

            if (challenge.isExpired()) {
                throw new ExpiredException("Challenge");
            }

            if (!challenge.getDeviceId().value().equals(command.deviceId())) {
                throw new NotFoundException("Challenge");
            }

            Device device = devicePort.findById(new DeviceId(command.deviceId())).orElseThrow(() -> new NotFoundException("Device"));

            device.checkRevoked();

            boolean verified = verifierPort.verify(
                    device.getEncryptionKey().value(),
                    challenge.getChallenge().value(),
                    command.signature()
            );

            if (!verified) {
                throw new InvalidSignatureException("Challenge");
            }

            return authTokenPort.generateTokenPair(device);
        });
    }
}