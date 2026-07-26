package com.paranoiax.users.application.ports.in.auth.refreshTokens;

import com.paranoiax.users.application.ports.out.operationResult.OperationCommand;

public record RefreshTokensCommand(
        String refreshToken,
        String operationId
) implements OperationCommand {
    @Override
    public String getPayloadSignature() {
        return String.join(":", refreshToken, operationId);
    }
}
