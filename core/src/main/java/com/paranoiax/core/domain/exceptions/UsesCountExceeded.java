package com.paranoiax.core.domain.exceptions;

public class UsesCountExceeded extends DomainException {
    public UsesCountExceeded() {
        super(DomainErrorCode.USES_COUNT_EXCEEDED);
    }
}
