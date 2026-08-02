package com.paranoiax.users.domain.exceptions;

public class RateLimitExceededException extends DomainException {
    private final long retryAfterMillis;

    public RateLimitExceededException(long retryAfterMillis) {
        super(DomainErrorCode.RATE_LIMIT_EXCEEDED);
        this.retryAfterMillis = retryAfterMillis;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }
}