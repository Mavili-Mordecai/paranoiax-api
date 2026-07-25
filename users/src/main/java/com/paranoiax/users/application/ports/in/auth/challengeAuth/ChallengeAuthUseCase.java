package com.paranoiax.users.application.ports.in.auth.challengeAuth;

import com.paranoiax.users.application.ports.in.auth.TokenPair;

public interface ChallengeAuthUseCase {
    TokenPair execute(ChallengeAuthCommand command);
}