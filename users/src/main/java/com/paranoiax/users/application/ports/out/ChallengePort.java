package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.challenge.Challenge;

import java.time.Duration;
import java.util.Optional;

public interface ChallengePort {
    Optional<Challenge> find(String challenge);
    Challenge save(Challenge challenge, Duration ttl);

    /**
     * If the port is implemented on top of the transactional storage,
     * then this method MUST be performed in a separate transaction to ensure security.
     *
     * @param challenge Challenge to delete
     * @return true if the challenge was deleted, false otherwise
     */
    boolean delete(Challenge challenge);
}
