package com.paranoiax.users.application.ports.out;

import java.time.Duration;
import java.util.UUID;

public interface AuthTokenBlacklistPort {
    boolean addIfAbsent(UUID tokenId, Duration ttl);
    boolean contains(UUID tokenId);
}