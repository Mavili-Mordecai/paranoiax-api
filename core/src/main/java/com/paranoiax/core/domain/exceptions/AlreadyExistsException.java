package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class AlreadyExistsException extends DomainException {
    public AlreadyExistsException(String resource) {
        super(
                DomainErrorCode.ALREADY_EXISTS,
                Map.of("resource", resource),
                String.format(DomainErrorCode.ALREADY_EXISTS.getDefaultMessage(), resource)
        );
    }
}
