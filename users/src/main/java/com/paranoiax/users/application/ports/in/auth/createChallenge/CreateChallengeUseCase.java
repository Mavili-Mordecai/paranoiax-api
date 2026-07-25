package com.paranoiax.users.application.ports.in.auth.createChallenge;

import com.paranoiax.users.domain.models.challenge.Challenge;

public interface CreateChallengeUseCase {
    Challenge execute(CreateChallengeCommand command);
}