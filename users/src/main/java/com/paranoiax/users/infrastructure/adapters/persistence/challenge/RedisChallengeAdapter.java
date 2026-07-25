package com.paranoiax.users.infrastructure.adapters.persistence.challenge;

import com.paranoiax.users.application.ports.out.ChallengePort;
import com.paranoiax.users.domain.models.challenge.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RedisChallengeAdapter implements ChallengePort {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisChallengeMapper mapper;

    @Override
    public Optional<Challenge> find(String challenge) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(getChallengeKey(challenge))).map((it) -> mapper.toDomain((RedisChallengeDto) it));
    }

    @Override
    public Challenge save(Challenge challenge, Duration ttl) {
        String key = getChallengeKey(challenge.getChallenge().value());
        Boolean saved = redisTemplate.opsForValue().setIfAbsent(key, mapper.toEntity(challenge), ttl);
        return Boolean.TRUE.equals(saved) ? challenge : null;
    }

    @Override
    public boolean delete(Challenge challenge) {
        return Boolean.TRUE.equals(redisTemplate.delete(getChallengeKey(challenge.getChallenge().value())));
    }

    private String getChallengeKey(String token) {
        return "challenge:" + token;
    }
}