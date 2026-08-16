package com.paranoiax.users.infrastructure.adapters.persistence.challenge;

import com.paranoiax.users.domain.models.challenge.ChallengeType;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RedisChallengeDto {
    private UUID deviceId;
    private ChallengeType type;
    private String challenge;
    private Instant createdAt;
    private Instant expiresAt;
}