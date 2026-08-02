package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class AlreadyRevokedException extends UnauthorizeException {
    public AlreadyRevokedException(String resource) {
        super(
                DomainErrorCode.ALREADY_REVOKED,
                Map.of("resource", resource),
                String.format(DomainErrorCode.ALREADY_REVOKED.getDefaultMessage(), resource)
        );
    }
}