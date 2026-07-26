package com.paranoiax.users.domain.exceptions;

public class OperationParametersChangedException extends DomainException {
    public OperationParametersChangedException() {
        super(DomainErrorCode.OPERATION_PARAMETERS_CHANGED);
    }
}
