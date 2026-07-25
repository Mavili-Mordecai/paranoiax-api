package com.paranoiax.users.infrastructure.adapters.persistence;

import com.paranoiax.users.application.ports.in.auth.TokenPair;
import com.paranoiax.users.infrastructure.common.OperationResultsMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthTokensMapper implements OperationResultsMapper<TokenPair, TokenPair> {
    @Override
    public Class<TokenPair> getDomainClass() {
        return TokenPair.class;
    }

    @Override
    public Class<TokenPair> getEntityClass() {
        return TokenPair.class;
    }

    @Override
    public TokenPair toEntity(TokenPair domain) {
        return domain;
    }

    @Override
    public TokenPair toDomain(TokenPair entity) {
        return entity;
    }
}