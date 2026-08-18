package com.paranoiax.users.application.ports.in.recoveryPoint.challenge;

import com.paranoiax.users.domain.models.challenge.Challenge;

public interface CreateRecoveryPointChallengeUseCase {
    Challenge execute(CreateRecoveryPointChallengeCommand command);
}
