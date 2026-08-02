package com.paranoiax.users.application.ports.out.rateLimiter;

import java.time.Duration;

public interface RateLimiter {
    void consume(String key, Duration window);
    void consume(String key, long maxQuota, Duration window);
    RateLimitResult tryConsume(String key, long maxQuota, Duration window);
}