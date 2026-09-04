package com.paranoiax.core_infra.adapters.crypto;

import com.paranoiax.core.application.ports.out.crypto.TokenGenerator;

import java.security.SecureRandom;
import java.util.Base64;

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
