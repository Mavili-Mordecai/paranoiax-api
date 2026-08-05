package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class InvalidStateTransitionException extends DomainException{
    public InvalidStateTransitionException(String from, String to) {
        super(
                DomainErrorCode.INVALID_STATE_TRANSITION,
                Map.of("from", from, "to", to),
                String.format(DomainErrorCode.INVALID_STATE_TRANSITION.getDefaultMessage(), from, to)
        );
    }
}