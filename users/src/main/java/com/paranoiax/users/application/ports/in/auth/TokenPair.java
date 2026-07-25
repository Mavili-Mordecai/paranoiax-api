package com.paranoiax.users.application.ports.in.auth;

import com.paranoiax.users.domain.Require;
import com.paranoiax.users.domain.exceptions.DomainErrorCode;

public record TokenPair(String accessToken, String refreshToken) {
    public TokenPair {
        Require.notBlank(accessToken, DomainErrorCode.MISSING_REQUIRED_FIELD, "accessToken");
        Require.notBlank(refreshToken, DomainErrorCode.MISSING_REQUIRED_FIELD, "refreshToken");
    }
}