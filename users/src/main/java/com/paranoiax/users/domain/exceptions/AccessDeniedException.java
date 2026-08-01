package com.paranoiax.users.domain.exceptions;

public class AccessDeniedException extends DomainException {
    public AccessDeniedException() {
        super(DomainErrorCode.ACCESS_DENIED);
    }
}