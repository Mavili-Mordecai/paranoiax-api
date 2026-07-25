package com.paranoiax.users.application.exceptions;

import com.paranoiax.users.domain.exceptions.DomainErrorCode;
import com.paranoiax.users.domain.exceptions.DomainException;

import java.util.Map;

public class LockAcquisitionFailedException extends DomainException {
    public LockAcquisitionFailedException(String operationId) {
        super(
                DomainErrorCode.LOCK_ACQUISITION_FAILED,
                Map.of("operationId", operationId),
                String.format(DomainErrorCode.LOCK_ACQUISITION_FAILED.getDefaultMessage(), operationId)
        );
    }
}