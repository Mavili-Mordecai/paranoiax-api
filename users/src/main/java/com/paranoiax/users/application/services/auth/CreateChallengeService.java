package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.createChallenge.CreateChallengeCommand;
import com.paranoiax.users.application.ports.in.auth.createChallenge.CreateChallengeUseCase;
import com.paranoiax.users.application.ports.out.ChallengePort;
import com.paranoiax.users.application.ports.out.DevicePort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.exceptions.NotFoundException;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.device.DeviceId;

import java.time.Duration;

public class CreateChallengeService implements CreateChallengeUseCase {
    private final ChallengePort challengePort;
    private final DevicePort devicePort;
    private final TokenGenerator tokenGenerator;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTll;
    private final int tokenSize;

    public CreateChallengeService(
            ChallengePort challengePort,
            DevicePort devicePort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTll,
            int tokenSize
    ) {
        this.devicePort = devicePort;
        this.executor = executor;
        this.challengePort = challengePort;
        this.tokenGenerator = tokenGenerator;
        this.lockTtl = lockTtl;
        this.resultTll = resultTll;
        this.tokenSize = tokenSize;
    }

    @Override
    public Challenge execute(CreateChallengeCommand command) {
        return executor.execute(command, Challenge.class, lockTtl, resultTll, () -> {
            Device device = devicePort.findById(new DeviceId(command.deviceId()))
                    .orElseThrow(() -> new NotFoundException("Device"));

            Challenge challenge = Challenge.create(
                    device.getId(),
                    new ChallengeValue(tokenGenerator.generate(tokenSize)),
                    resultTll
            );
            return challengePort.save(challenge, resultTll);
        });
    }
}