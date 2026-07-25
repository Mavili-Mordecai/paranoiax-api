package com.paranoiax.users.domain.models.challenge;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

public record ChallengeValue(String value) {
    public ChallengeValue {
        Require.notBlank(value, DomainErrorCode.EMPTY_VALUE_NOT_ALLOWED, "challenge");
    }
}