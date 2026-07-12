package com.paranoiax.users.domain.exceptions;

public class DomainValidationException extends DomainException {
    public DomainValidationException(String message) {
        super(message);
    }
}
