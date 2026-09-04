package com.paranoiax.users.infrastructure.rest.api.recoveryPoint.v1;

import com.paranoiax.core.domain.EncryptionKey;
import com.paranoiax.core.domain.IdentityKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateRecoveryPointRequest(
        @NotBlank(message = "FIELD_REQUIRED")
        @Size(min = IdentityKey.MIN_SIZE, max = IdentityKey.MAX_SIZE, message = "INVALID_LENGTH")
        String identityKey,
        @NotBlank(message = "FIELD_REQUIRED")
        @Size(min = EncryptionKey.MIN_SIZE, max = EncryptionKey.MAX_SIZE, message = "INVALID_LENGTH")
        String encryptedData
) {
}