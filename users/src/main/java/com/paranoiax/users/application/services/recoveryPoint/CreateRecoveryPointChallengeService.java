package com.paranoiax.users.application.services.recoveryPoint;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.exceptions.AccessDeniedException;
import com.paranoiax.users.application.ports.in.recoveryPoint.challenge.CreateRecoveryPointChallengeCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.challenge.CreateRecoveryPointChallengeUseCase;
import com.paranoiax.users.application.ports.out.ChallengePort;
import com.paranoiax.users.application.ports.out.UserPort;
import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.users.application.services.OperationExecutor;
import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.challenge.ChallengeType;
import com.paranoiax.users.domain.models.user.User;
import com.paranoiax.users.domain.models.user.Username;

import java.time.Duration;

public class CreateRecoveryPointChallengeService implements CreateRecoveryPointChallengeUseCase {
    private final ChallengePort challengePort;
    private final UserPort userPort;
    private final TokenGenerator tokenGenerator;
    private final OperationExecutor executor;
    private final Duration lockTtl;
    private final Duration resultTll;
    private final int tokenSize;

    public CreateRecoveryPointChallengeService(
            ChallengePort challengePort,
            UserPort userPort,
            TokenGenerator tokenGenerator,
            OperationExecutor executor,
            Duration lockTtl,
            Duration resultTll,
            int tokenSize
    ) {
        this.challengePort = challengePort;
        this.userPort = userPort;
        this.tokenGenerator = tokenGenerator;
        this.executor = executor;
        this.lockTtl = lockTtl;
        this.resultTll = resultTll;
        this.tokenSize = tokenSize;
    }

    @Override
    public Challenge execute(CreateRecoveryPointChallengeCommand command) {
        return executor.execute(command, Challenge.class, lockTtl, resultTll, () -> {
            User user = userPort.findByUsername(new Username(command.username()))
                    .orElseThrow(AccessDeniedException::new);

            return challengePort.save(Challenge.create(
                    user.getId(),
                    new DeviceId(command.deviceId()),
                    ChallengeType.ACCOUNT_RECOVERY,
                    new ChallengeValue(tokenGenerator.generate(tokenSize)),
                    resultTll
            ), resultTll);
        });
    }
}
