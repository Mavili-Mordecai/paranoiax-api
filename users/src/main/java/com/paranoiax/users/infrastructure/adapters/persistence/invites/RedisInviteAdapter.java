package com.paranoiax.users.infrastructure.adapters.persistence.invites;

import com.paranoiax.users.application.ports.out.InvitePort;
import com.paranoiax.users.domain.models.invite.Invite;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisInviteAdapter implements InvitePort {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisInviteMapper mapper;

    @Override
    public Optional<Invite> findByToken(String token) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(getInviteKey(token))).map((it) -> mapper.toDomain((RedisInviteDto) it));
    }

    @Override
    public Invite save(Invite invite, Duration ttl) {
        String key = getInviteKey(invite.getRegistrationToken().value());
        Boolean saved = redisTemplate.opsForValue().setIfAbsent(key, mapper.toDto(invite), ttl);
        return Boolean.TRUE.equals(saved) ? invite : null;
    }

    @Override
    public boolean delete(Invite invite) {
        return Boolean.TRUE.equals(redisTemplate.delete(getInviteKey(invite.getRegistrationToken().value())));
    }

    private String getInviteKey(String token) {
        return "invite:" + token;
    }
}
