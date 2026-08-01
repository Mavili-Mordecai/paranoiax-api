package com.paranoiax.users.domain.exceptions;

import java.util.Map;

public class InvalidValueException extends DomainException {
    public InvalidValueException(String field) {
        super(
                DomainErrorCode.INVALID_VALUE,
                Map.of("field", field),
                String.format(DomainErrorCode.INVALID_VALUE.getDefaultMessage(), field)
        );
    }
}