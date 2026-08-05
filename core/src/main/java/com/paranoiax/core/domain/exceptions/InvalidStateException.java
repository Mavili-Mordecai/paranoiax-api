package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class InvalidStateException extends DomainException{
    public InvalidStateException(String action, String status) {
        super(
                DomainErrorCode.INVALID_STATE,
                Map.of("action", action, "status", status),
                String.format(DomainErrorCode.INVALID_VALUE.getDefaultMessage(), action, status)
        );
    }
}