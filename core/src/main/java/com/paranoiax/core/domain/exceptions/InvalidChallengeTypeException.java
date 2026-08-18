package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class InvalidChallengeTypeException extends DomainException {
    public InvalidChallengeTypeException(String type) {
        super(
                DomainErrorCode.INVALID_CHALLENGE_TYPE,
                Map.of("resource", type),
                String.format(DomainErrorCode.INVALID_CHALLENGE_TYPE.getDefaultMessage(), type)
        );
    }
}
