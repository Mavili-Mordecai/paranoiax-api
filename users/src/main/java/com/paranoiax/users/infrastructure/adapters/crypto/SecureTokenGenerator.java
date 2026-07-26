package com.paranoiax.users.infrastructure.adapters.crypto;

import com.paranoiax.users.application.ports.out.crypto.TokenGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class SecureTokenGenerator implements TokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder();

    @Override
    public String generate(int size) {
        byte[] tokenBytes = new byte[size];
        secureRandom.nextBytes(tokenBytes);
        return base64Encoder.encodeToString(tokenBytes);
    }
}
