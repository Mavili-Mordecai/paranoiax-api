package com.paranoiax.users.infrastructure.common;

public interface OperationResultsMapper<DOMAIN, DTO> {
    Class<DOMAIN> getDomainClass();
    Class<DTO> getDtoClass();

    DTO toDto(DOMAIN domain);
    DOMAIN toDomain(DTO dto);
}
