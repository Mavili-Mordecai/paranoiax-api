package com.paranoiax.users.infrastructure.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.kafka.autoconfigure.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.ConversionException;
import org.springframework.kafka.support.converter.JacksonJsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJacksonJavaTypeMapper;
import org.springframework.kafka.support.mapping.JacksonJavaTypeMapper;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class KafkaConfig {

    @Bean
    public RecordMessageConverter messageConverter(JsonMapper jsonMapper) {
        JacksonJsonMessageConverter messageConverter = new JacksonJsonMessageConverter(jsonMapper);
        DefaultJacksonJavaTypeMapper typeMapper = new DefaultJacksonJavaTypeMapper();

        typeMapper.addTrustedPackages("com.paranoiax");
        typeMapper.setTypePrecedence(JacksonJavaTypeMapper.TypePrecedence.INFERRED);

        messageConverter.setTypeMapper(typeMapper);

        return messageConverter;
    }

    @Bean
    public DefaultKafkaProducerFactoryCustomizer serializerCustomizer() {
        return producerFactory -> producerFactory.setValueSerializer(new JacksonJsonSerializer<>());
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                kafkaTemplate,
                (record, exception) -> {
                    Throwable cause = exception.getCause();

                    if (exception instanceof ConversionException || cause instanceof ConversionException) {
                        return new TopicPartition(record.topic() + ".serde.DLT", record.partition());
                    }

                    return new TopicPartition(record.topic() + ".DLT", record.partition());
                }
        );

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(0L, 2L));
        errorHandler.addNotRetryableExceptions(ConversionException.class);

        return errorHandler;
    }
}