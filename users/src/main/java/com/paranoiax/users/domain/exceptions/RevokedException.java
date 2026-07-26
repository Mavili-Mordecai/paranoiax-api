package com.paranoiax.users.domain.exceptions;

import java.util.Map;

public class RevokedException extends UnauthorizeException {
    public RevokedException(String resource) {
        super(
                DomainErrorCode.ALREADY_REVOKED,
                Map.of("resource", resource),
                String.format(DomainErrorCode.ALREADY_REVOKED.getDefaultMessage(), resource)
        );
    }
}