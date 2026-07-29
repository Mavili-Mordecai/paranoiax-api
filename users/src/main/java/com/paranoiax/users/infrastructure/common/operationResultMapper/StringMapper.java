package com.paranoiax.users.infrastructure.common.operationResultMapper;

import org.springframework.stereotype.Component;

@Component
public class StringMapper implements OperationResultsMapper<String, String> {
    @Override
    public Class<String> getDomainClass() {
        return String.class;
    }

    @Override
    public Class<String> getEntityClass() {
        return String.class;
    }

    @Override
    public String toEntity(String s) {
        return s;
    }

    @Override
    public String toDomain(String s) {
        return s;
    }
}