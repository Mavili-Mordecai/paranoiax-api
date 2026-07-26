package com.paranoiax.users.application.ports.in.auth.refreshTokens;

import com.paranoiax.users.application.ports.in.auth.TokenPair;

public interface RefreshTokensUseCase {
    TokenPair execute(RefreshTokensCommand command);
}