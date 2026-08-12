package com.paranoiax.core.domain.exceptions;

public class InvalidFriendOperationException extends DomainException {
    public InvalidFriendOperationException() {
        super(DomainErrorCode.INVALID_FRIEND_OPERATION);
    }
}