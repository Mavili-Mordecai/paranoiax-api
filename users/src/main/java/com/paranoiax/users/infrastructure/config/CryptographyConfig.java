package com.paranoiax.users.infrastructure.config;

import com.paranoiax.core.application.ports.out.crypto.HashPort;
import com.paranoiax.core.application.ports.out.crypto.SignatureVerifierPort;
import com.paranoiax.core.application.ports.out.crypto.TokenGenerator;
import com.paranoiax.core_infra.adapters.crypto.ApacheHashAdapter;
import com.paranoiax.core_infra.adapters.crypto.Ed25519SignatureVerifierAdapter;
import com.paranoiax.core_infra.adapters.crypto.SecureTokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptographyConfig {

    @Bean
    public HashPort hashPort() {
        return new ApacheHashAdapter();
    }

    @Bean
    public TokenGenerator tokenGenerator() {
        return new SecureTokenGenerator();
    }

    @Bean
    public SignatureVerifierPort signatureVerifierPort() {
        return new Ed25519SignatureVerifierAdapter();
    }
}
