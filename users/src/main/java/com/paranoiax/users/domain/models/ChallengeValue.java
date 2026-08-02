package com.paranoiax.users.domain.models;

import com.paranoiax.core.domain.Require;
import com.paranoiax.core.domain.exceptions.DomainErrorCode;

public record ChallengeValue(String value) {
    public ChallengeValue {
        Require.notBlank(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "challenge");
    }
}