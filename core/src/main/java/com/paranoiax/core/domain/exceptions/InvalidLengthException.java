package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class InvalidLengthException extends DomainException {
    public InvalidLengthException(String field, Integer minLength, Integer maxLength) {
        super(
                DomainErrorCode.INVALID_LENGTH,
                Map.of(
                        "field", field,
                        "minLength", minLength,
                        "maxLength", maxLength
                ),
                String.format(DomainErrorCode.INVALID_LENGTH.getDefaultMessage(), field, minLength, maxLength)
        );
    }
}
