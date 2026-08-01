package com.paranoiax.users.domain.exceptions;

import java.util.Map;

public class AlreadyTakenException extends DomainException {
    public AlreadyTakenException(String resource) {
        super(
                DomainErrorCode.ALREADY_TAKEN,
                Map.of("resource", resource),
                String.format(DomainErrorCode.ALREADY_TAKEN.getDefaultMessage(), resource)
        );
    }
}