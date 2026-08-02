package com.paranoiax.users.application.ports.out.rateLimiter;

public record RateLimitResult(
        boolean consumed,
        long remainingQuota,
        long retryAfterMillis
) {
    public static RateLimitResult failed(long retryAfterMillis) {
        return new RateLimitResult(false, 0, retryAfterMillis);
    }

    public static RateLimitResult success(long remainingQuota, long retryAfterMillis) {
        return new RateLimitResult(true, remainingQuota, retryAfterMillis);
    }
}