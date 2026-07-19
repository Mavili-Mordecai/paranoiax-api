package com.paranoiax.users.infrastructure.adapters.persistence.operationResults;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paranoiax.users.application.ports.out.OperationResultPort;
import com.paranoiax.users.infrastructure.common.OperationResultsMapper;
import com.paranoiax.users.infrastructure.exceptions.InfrastructureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RedisOperationResultAdapter implements OperationResultPort {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final Map<Class<?>, OperationResultsMapper<?, ?>> mappers;

    public RedisOperationResultAdapter(
            RedisTemplate<String, String> redisTemplate,
            ObjectMapper objectMapper,
            List<OperationResultsMapper<?, ?>> mapperList
    ) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.mappers = mapperList.stream().collect(Collectors.toMap(
                OperationResultsMapper::getDomainClass,
                Function.identity(),
                (a, b) -> a)
        );
    }

    @Override
    public boolean tryLock(String operationId, Duration ttl) {
        Boolean acquired = redisTemplate.opsForValue().setIfAbsent(getLockKey(operationId), "\"LOCKED\"", ttl);
        return Boolean.TRUE.equals(acquired);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> findResult(String operationId, Class<T> clazz) {
        OperationResultsMapper<T, Object> mapper = (OperationResultsMapper<T, Object>) getMapperOrThrow(clazz);

        String json = redisTemplate.opsForValue().get(getOperationKey(operationId));
        if (json == null) {
            return Optional.empty();
        }

        try {
            Object result = objectMapper.readValue(json, mapper.getDtoClass());
            return Optional.of(mapper.toDomain(result));
        } catch (JsonProcessingException e) {
            throw new InfrastructureException("Failed to read operation result from Redis", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void saveResult(String operationId, T result, Duration ttl) {
        OperationResultsMapper<T, Object> mapper = (OperationResultsMapper<T, Object>) getMapperOrThrow(result.getClass());

        try {
            Object dto = mapper.toDto(result);
            String json = objectMapper.writeValueAsString(dto);
            redisTemplate.opsForValue().set(getOperationKey(operationId), json, ttl);
        } catch (JsonProcessingException ex) {
            throw new InfrastructureException("Failed to save operation result to Redis", ex);
        }
    }

    private OperationResultsMapper<?, ?> getMapperOrThrow(Class<?> clazz) {
        OperationResultsMapper<?, ?> mapper = mappers.get(clazz);
        if (mapper == null) {
            throw new IllegalArgumentException("No IdempotencyMapper registered for class: " + clazz.getName());
        }
        return mapper;
    }

    private String getLockKey(String operationId) {
        return "operation:lock:" + operationId;
    }

    private String getOperationKey(String operationId) {
        return "operation:" + operationId;
    }
}
