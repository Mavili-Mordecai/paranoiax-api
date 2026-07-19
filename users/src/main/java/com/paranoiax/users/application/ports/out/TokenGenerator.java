package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.invite.RegistrationToken;

public interface TokenGenerator {
    RegistrationToken generate();
}
