package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class RevokedException extends UnauthorizeException {
    public RevokedException(String resource) {
        super(
                DomainErrorCode.REVOKED,
                Map.of("resource", resource),
                String.format(DomainErrorCode.REVOKED.getDefaultMessage(), resource)
        );
    }
}