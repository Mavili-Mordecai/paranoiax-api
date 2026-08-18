package com.paranoiax.users.infrastructure.adapters.persistence.recoveryPoint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedisRecoveryPointDto {
    private UUID id;
    private UUID userId;
    private String identityKey;
    private String encryptedData;
    private Instant createdAt;
}
