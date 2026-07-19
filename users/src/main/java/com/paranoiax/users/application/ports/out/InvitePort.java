package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.invite.Invite;

import java.time.Duration;
import java.util.Optional;

public interface InvitePort {
    Optional<Invite> findByToken(String token);
    Invite save(Invite invite, Duration ttl);
    boolean delete(Invite invite);
}
