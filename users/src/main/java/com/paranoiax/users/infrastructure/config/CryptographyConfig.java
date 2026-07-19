package com.paranoiax.users.infrastructure.config;

import com.paranoiax.users.application.ports.out.TokenGenerator;
import com.paranoiax.users.infrastructure.adapters.crypto.SecureTokenGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptographyConfig {

    @Bean
    public TokenGenerator tokenGenerator() {
        return new SecureTokenGenerator();
    }
}
