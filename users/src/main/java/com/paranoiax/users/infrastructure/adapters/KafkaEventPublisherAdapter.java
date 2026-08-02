package com.paranoiax.users.infrastructure.adapters;

import com.paranoiax.users.application.ports.out.EventPublisher;
import com.paranoiax.users.domain.models.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KafkaEventPublisherAdapter implements EventPublisher {
    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void publish(DomainEvent event) {
        kafkaTemplate.send(resolveTopicName(event), event);
    }

    /**
     * Resolves the Kafka topic name for a given domain event based on its class name.
     * <p>
     * The generated topic follows the standard naming convention: {@code service.entity.action.version}.
     * For example, a {@code DeviceMigrationCreatedEvent} will be mapped to
     * {@code [applicationName].device-migration.created.[version]}.
     * </p>
     *
     * @param event the domain event to publish
     * @return the formatted topic name string
     */
    private String resolveTopicName(DomainEvent event) {
        String className = event.getClass().getSimpleName().replace("Event", "");
        String[] words = className.split("(?=\\p{Upper})");

        String entity = Arrays.stream(words)
                .limit(words.length - 1)
                .map(String::toLowerCase)
                .collect(Collectors.joining("-"));

        String action = words[words.length - 1].toLowerCase();

        return String.format("%s.%s.%s.%s", applicationName, entity, action, event.version());
    }
}