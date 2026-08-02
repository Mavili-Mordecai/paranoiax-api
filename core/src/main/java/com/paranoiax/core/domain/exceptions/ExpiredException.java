package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class ExpiredException extends UnauthorizeException {
    public ExpiredException(String resource) {
        super(
                DomainErrorCode.EXPIRED_EXCEPTION,
                Map.of("resource", resource),
                String.format(DomainErrorCode.EXPIRED_EXCEPTION.getDefaultMessage(), resource)
        );
    }
}
