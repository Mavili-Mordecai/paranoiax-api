package com.paranoiax.core.domain.exceptions;

public class OperationParametersChangedException extends DomainException {
    public OperationParametersChangedException() {
        super(DomainErrorCode.OPERATION_PARAMETERS_CHANGED);
    }
}
