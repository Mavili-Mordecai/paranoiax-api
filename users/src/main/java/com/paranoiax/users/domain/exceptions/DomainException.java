package com.paranoiax.users.domain.exceptions;

import java.util.Map;

public class DomainException extends RuntimeException {
    private final DomainErrorCode code;
    private final Map<String, Object> args;

    public DomainException(DomainErrorCode code) {
        super(code.getDefaultMessage());
        this.code = code;
        this.args = null;
    }

    public DomainException(DomainErrorCode code, Map<String, Object> args) {
        super(code.getDefaultMessage());
        this.code = code;
        this.args = args;
    }

    public DomainException(DomainErrorCode code, Map<String, Object> args, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
        this.args = args;
    }

    public DomainErrorCode getCode() {
        return code;
    }

    public Map<String, Object> getArgs() {
        return args;
    }
}
