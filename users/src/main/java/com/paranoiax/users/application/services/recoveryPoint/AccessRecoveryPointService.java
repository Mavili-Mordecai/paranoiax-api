package com.paranoiax.users.application.services.recoveryPoint;

import com.paranoiax.core.domain.exceptions.*;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.AccessRecoveryPointCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.AccessRecoveryPointUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.RecoveryPointDetails;
import com.paranoiax.users.application.ports.out.ChallengePort;
import com.paranoiax.users.application.ports.out.RecoveryPointPort;
import com.paranoiax.users.application.services.RecoveryChallengeValidator;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;

public class AccessRecoveryPointService implements AccessRecoveryPointUseCase {
    private final RecoveryPointPort recoveryPointPort;
    private final ChallengePort challengePort;
    private final RecoveryChallengeValidator validator;

    public AccessRecoveryPointService(
            RecoveryPointPort recoveryPointPort,
            ChallengePort challengePort,
            RecoveryChallengeValidator validator
    ) {
        this.recoveryPointPort = recoveryPointPort;
        this.challengePort = challengePort;
        this.validator = validator;
    }

    @Override
    public RecoveryPointDetails execute(AccessRecoveryPointCommand command) {
        Challenge challenge = challengePort.find(command.challenge())
                .orElseThrow(() -> new NotFoundException("Challenge"));

        validator.validateChallenge(command.deviceId(), challenge);

        RecoveryPoint recoveryPoint = recoveryPointPort.find(challenge.getUserId())
                .orElseThrow(() -> new NotFoundException("RecoveryPoint"));

        validator.validateRecoveryPoint(command.signature(), recoveryPoint, challenge);

        return RecoveryPointDetails.from(recoveryPoint);
    }
}
