package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class InvalidFormatException extends UnauthorizeException {
    public InvalidFormatException(String field) {
        super(
                DomainErrorCode.INVALID_FORMAT,
                Map.of("field", field),
                String.format(DomainErrorCode.INVALID_FORMAT.getDefaultMessage(), field)
        );
    }
}
