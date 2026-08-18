package com.paranoiax.users.infrastructure.rest.api.recoveryPoint.v1;

import com.paranoiax.users.domain.models.user.Username;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateRecoveryPointChallengeRequest(
        @NotBlank(message = "FIELD_REQUIRED")
        @Size(min = Username.MIN_LENGTH, max = Username.MAX_LENGTH, message = "INVALID_LENGTH")
        String username
) {
}
