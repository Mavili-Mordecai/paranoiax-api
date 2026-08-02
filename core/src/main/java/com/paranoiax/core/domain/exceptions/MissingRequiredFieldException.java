package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class MissingRequiredFieldException extends DomainException {
    public MissingRequiredFieldException(String fieldName) {
        super(
                DomainErrorCode.MISSING_REQUIRED_FIELD,
                Map.of("field", fieldName),
                String.format(DomainErrorCode.MISSING_REQUIRED_FIELD.getDefaultMessage(), fieldName)
        );
    }
}