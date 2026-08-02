package com.paranoiax.users.domain.models;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredOn();

    default String version() {
        return "v1";
    }
}