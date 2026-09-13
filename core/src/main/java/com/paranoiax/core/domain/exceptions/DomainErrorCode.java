package com.paranoiax.core.domain.exceptions;

public enum DomainErrorCode {
    MISSING_REQUIRED_FIELD("\"%s\" is missing"),
    EMPTY_VALUE_NOT_ALLOWED("Value for \"%s\" cannot be empty"),
    INVALID_LENGTH("\"%s\" must be between %d and %d"),
    INVALID_VALUE("Invalid value for \"%s\""),
    INVALID_FORMAT("Invalid format for \"%s\""),
    INVALID_STATE("It is impossible to %s from the %s status"),
    INVALID_STATE_TRANSITION("It is not possible to switch from the %s status to the %s status"),
    INVALID_FRIEND_OPERATION("Invalid operation with this friend"),
    INVALID_CHALLENGE_TYPE("Invalid challenge type: \"%s\""),
    INVALID_CHAT_TYPE("Invalid chat type: \"%s\". Expected: %s"),
    TIMESTAMP_MUST_BE_AFTER("Field \"%s\" must be after \"%s\""),
    TIMESTAMP_MUST_BE_BEFORE("Field \"%s\" must be before \"%s\""),
    LOCK_ACQUISITION_FAILED("Failed to acquire lock for operation. Operation ID: %s"),
    NOT_FOUND("%s not found"),
    EXPIRED_EXCEPTION("%s has expired"),
    INVALID_SIGNATURE("%s has an invalid signature"),
    REVOKED("%s revoked"),
    ALREADY_REVOKED("%s already revoked"),
    ALREADY_EXISTS("%s already exists"),
    OPERATION_PARAMETERS_CHANGED("Operation already processed with different parameters"),
    ACCESS_DENIED("Access denied"),
    ALREADY_TAKEN("%s already taken"),
    RATE_LIMIT_EXCEEDED("Rate limit exceeded"),
    USES_COUNT_EXCEEDED("Uses count exceeded");

    private final String defaultMessage;

    DomainErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}