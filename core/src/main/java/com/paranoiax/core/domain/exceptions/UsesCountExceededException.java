package com.paranoiax.core.domain.exceptions;

public class UsesCountExceededException extends DomainException {
    public UsesCountExceededException() {
        super(DomainErrorCode.USES_COUNT_EXCEEDED);
    }
}
