package com.paranoiax.users.domain.exceptions;

import java.util.Map;

public class InvalidSignatureException extends UnauthorizeException {
    public InvalidSignatureException(String field) {
        super(
                DomainErrorCode.INVALID_SIGNATURE,
                Map.of("field", field),
                String.format(DomainErrorCode.INVALID_SIGNATURE.getDefaultMessage(), field)
        );
    }
}