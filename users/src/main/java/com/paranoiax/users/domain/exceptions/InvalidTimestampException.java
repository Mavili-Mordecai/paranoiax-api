package com.paranoiax.users.domain.exceptions;

import java.util.Map;

public class InvalidTimestampException extends DomainException {
    public InvalidTimestampException(String fieldName1, DomainErrorCode code, String fieldName2) {
        super(
                code,
                Map.of("fieldName1", fieldName1, "fieldName2", fieldName2),
                String.format(code.getDefaultMessage(), fieldName1, fieldName2)
        );
    }
}