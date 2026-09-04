package com.paranoiax.core_infra.operationResultMapper;

public interface OperationResultsMapper<DOMAIN, ENTITY> {
    Class<DOMAIN> getDomainClass();
    Class<ENTITY> getEntityClass();

    ENTITY toEntity(DOMAIN domain);
    DOMAIN toDomain(ENTITY entity);
}
