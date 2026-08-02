package com.paranoiax.users.infrastructure.adapters.persistence.challenge;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.users.domain.models.challenge.Challenge;
import com.paranoiax.users.domain.models.ChallengeValue;
import com.paranoiax.users.infrastructure.common.operationResultMapper.OperationResultsMapper;
import org.springframework.stereotype.Component;

@Component
public class RedisChallengeMapper implements OperationResultsMapper<Challenge, RedisChallengeDto> {
    @Override
    public Class<Challenge> getDomainClass() {
        return Challenge.class;
    }

    @Override
    public Class<RedisChallengeDto> getEntityClass() {
        return RedisChallengeDto.class;
    }

    @Override
    public RedisChallengeDto toEntity(Challenge domain) {
        return new RedisChallengeDto(
                domain.getDeviceId().value(),
                domain.getChallenge().value(),
                domain.getCreatedAt(),
                domain.getExpiresAt()
        );
    }

    @Override
    public Challenge toDomain(RedisChallengeDto entity) {
        return new Challenge(
                new DeviceId(entity.getDeviceId()),
                new ChallengeValue(entity.getChallenge()),
                entity.getCreatedAt(),
                entity.getExpiresAt()
        );
    }
}