package com.paranoiax.users.application.services.auth;

import com.paranoiax.users.application.ports.in.auth.createChallenge.CreateChallengeCommand;
import com.paranoiax.users.application.ports.in.auth.createChallenge.CreateChallengeUseCase;
import com.paranoiax.users.application.ports.out.ChallengePort;
import com.paranoiax.users.application.ports.out.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.challenge.ChallengeValue;
import com.paranoiax.users.domain.models.device.DeviceId;

import java.time.Duration;

public class CreateChallengeService implements CreateChallengeUseCase {
    private final ChallengePort challengePort;
    private final TokenGenerator tokenGenerator;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTll;
    private final int size = 32;

    public CreateChallengeService(
            ChallengePort challengePort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTll
    ) {
        this.executor = executor;
        this.challengePort = challengePort;
        this.tokenGenerator = tokenGenerator;
        this.lockTtl = lockTtl;
        this.resultTll = resultTll;
    }

    @Override
    public Challenge execute(CreateChallengeCommand command) {
        return executor.execute(command.operationId(), Challenge.class, lockTtl, resultTll, () -> {
            Challenge challenge = Challenge.create(
                    new DeviceId(command.deviceId()),
                    new ChallengeValue(tokenGenerator.generate(size)),
                    resultTll
            );
            return challengePort.save(challenge, resultTll);
        });
    }
}