package com.paranoiax.core.domain.exceptions;

import java.util.Map;

public class InvalidChatTypeException extends DomainException {
    public InvalidChatTypeException(String actual, String expected) {
        super(
                DomainErrorCode.INVALID_CHAT_TYPE,
                Map.of("actual", actual, "expected", expected),
                String.format(DomainErrorCode.INVALID_CHAT_TYPE.getDefaultMessage(), actual, expected)
        );
    }
}
