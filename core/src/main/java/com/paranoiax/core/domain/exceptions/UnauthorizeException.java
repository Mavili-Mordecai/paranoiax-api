package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class UnauthorizeException extends DomainException {
    public UnauthorizeException(DomainErrorCode code, Map<String, Object> args, String defaultMessage) {
        super(code, args, defaultMessage);
    }
}