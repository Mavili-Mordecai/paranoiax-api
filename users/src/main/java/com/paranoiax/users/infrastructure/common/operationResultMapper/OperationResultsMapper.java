package com.paranoiax.users.infrastructure.common.operationResultMapper;

public interface OperationResultsMapper<DOMAIN, ENTITY> {
    Class<DOMAIN> getDomainClass();
    Class<ENTITY> getEntityClass();

    ENTITY toEntity(DOMAIN domain);
    DOMAIN toDomain(ENTITY entity);
}
