package com.paranoiax.users.infrastructure.adapters.crypto;

import com.paranoiax.users.application.ports.out.TokenGenerator;
import com.paranoiax.users.domain.models.invite.RegistrationToken;

import java.security.SecureRandom;
import java.util.Base64;

public class SecureTokenGenerator implements TokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    @Override
    public RegistrationToken generate() {
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        String token = base64Encoder.encodeToString(tokenBytes);
        return new RegistrationToken(token);
    }
}
