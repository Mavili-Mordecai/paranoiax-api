package com.paranoiax.chats.infrastructure.config;

import com.paranoiax.core_infra.operationResultMapper.StringMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OperationResultsConfig {

    @Bean
    public StringMapper stringMapper() {
        return new StringMapper();
    }
}
