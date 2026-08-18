package com.paranoiax.users.application.services.recoveryPoint;

import com.paranoiax.core.domain.exceptions.*;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.AccessRecoveryPointCommand;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.AccessRecoveryPointUseCase;
import com.paranoiax.users.application.ports.in.recoveryPoint.get.RecoveryPointDetails;
import com.paranoiax.users.application.ports.out.ChallengePort;
import com.paranoiax.users.application.ports.out.RecoveryPointPort;
import com.paranoiax.users.application.ports.out.crypto.SignatureVerifierPort;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.challenge.ChallengeType;
import com.paranoiax.users.domain.models.recoveryPoint.RecoveryPoint;

public class AccessRecoveryPointService implements AccessRecoveryPointUseCase {
    private final RecoveryPointPort recoveryPointPort;
    private final ChallengePort challengePort;
    private final SignatureVerifierPort verifierPort;

    public AccessRecoveryPointService(
            RecoveryPointPort recoveryPointPort,
            ChallengePort challengePort,
            SignatureVerifierPort verifierPort
    ) {
        this.recoveryPointPort = recoveryPointPort;
        this.challengePort = challengePort;
        this.verifierPort = verifierPort;
    }

    @Override
    public RecoveryPointDetails execute(AccessRecoveryPointCommand command) {
        Challenge challenge = challengePort.find(command.challenge())
                .orElseThrow(() -> new NotFoundException("Challenge"));

        challengePort.delete(challenge);

        validateCommand(command, challenge);

        RecoveryPoint recoveryPoint = recoveryPointPort.findAll(challenge.getUserId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("RecoveryPoint"));

        checkRecoveryPoint(command, recoveryPoint, challenge);

        return RecoveryPointDetails.from(recoveryPoint);
    }

    private void checkRecoveryPoint(AccessRecoveryPointCommand command, RecoveryPoint recoveryPoint, Challenge challenge) {
        boolean verified = verifierPort.verify(
                recoveryPoint.getIdentityKey().value(),
                challenge.getChallenge().value(),
                command.signature()
        );

        if (!verified) {
            throw new InvalidSignatureException("Challenge");
        }
    }

    private static void validateCommand(AccessRecoveryPointCommand command, Challenge challenge) {
        if (challenge.getType() != ChallengeType.ACCOUNT_RECOVERY) {
            throw new InvalidChallengeTypeException(challenge.getType().name());
        }

        if (!command.deviceId().equals(challenge.getDeviceId().value())) {
            throw new AccessDeniedException();
        }

        if (challenge.isExpired()) {
            throw new ExpiredException("Challenge");
        }
    }
}
