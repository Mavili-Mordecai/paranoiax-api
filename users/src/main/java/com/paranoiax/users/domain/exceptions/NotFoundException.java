package com.paranoiax.users.domain.exceptions;

import java.util.Map;

public class NotFoundException extends DomainException {
    public NotFoundException(String resource) {
        super(
                DomainErrorCode.NOT_FOUND,
                Map.of("resource", resource),
                String.format(DomainErrorCode.NOT_FOUND.getDefaultMessage(), resource)
        );
    }
}
