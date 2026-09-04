package com.paranoiax.users.application.ports.in.auth.refreshTokens;

import com.paranoiax.core.application.OperationCommand;

public record RefreshTokensCommand(
        String refreshToken,
        String operationId
) implements OperationCommand {
}
