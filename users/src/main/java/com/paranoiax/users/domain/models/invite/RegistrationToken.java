package com.paranoiax.users.domain.models.invite;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

public record RegistrationToken(String value) {
    public RegistrationToken {
        Require.notNull(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "registrationToken");
    }
}
