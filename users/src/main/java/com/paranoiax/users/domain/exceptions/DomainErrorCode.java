package com.paranoiax.users.domain.exceptions;

public enum DomainErrorCode {
    MISSING_REQUIRED_FIELD("\"%s\" is missing"),
    EMPTY_VALUE_NOT_ALLOWED("Value for \"%s\" cannot be empty"),
    INVALID_LENGTH("\"%s\" must be between %d and %d characters long"),
    INVALID_FORMAT("Invalid format for \"%s\""),
    TIMESTAMP_MUST_BE_AFTER("Field '%s' must be after '%s'"),
    TIMESTAMP_MUST_BE_BEFORE("Field '%s' must be before '%s'");

    private final String defaultMessage;

    DomainErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
