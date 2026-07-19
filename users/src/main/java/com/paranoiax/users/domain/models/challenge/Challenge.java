package com.paranoiax.users.domain.models.challenge;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

public record Challenge(String value) {
    public Challenge {
        Require.notBlank(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "challenge");
    }
}