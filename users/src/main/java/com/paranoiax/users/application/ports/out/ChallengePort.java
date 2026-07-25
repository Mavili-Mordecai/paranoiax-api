package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.challenge.Challenge;

import java.time.Duration;
import java.util.Optional;

public interface ChallengePort {
    Optional<Challenge> find(String challenge);
    Challenge save(Challenge challenge, Duration ttl);
    boolean delete(Challenge challenge);
}
