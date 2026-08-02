package com.paranoiax.core.domain.exceptions;

public class AccessDeniedException extends DomainException {
    public AccessDeniedException() {
        super(DomainErrorCode.ACCESS_DENIED);
    }
}