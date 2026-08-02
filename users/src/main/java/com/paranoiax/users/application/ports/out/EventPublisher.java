package com.paranoiax.users.application.ports.out;

import com.paranoiax.users.domain.models.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);
}