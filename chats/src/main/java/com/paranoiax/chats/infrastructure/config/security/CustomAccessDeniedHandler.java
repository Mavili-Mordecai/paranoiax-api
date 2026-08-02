package com.paranoiax.chats.infrastructure.config.security;

import com.paranoiax.core_infra.rest.exceptions.ApiErrorCode;
import com.paranoiax.core_infra.rest.exceptions.DomainErrorResponse;
import com.paranoiax.core_infra.rest.exceptions.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(ErrorResponse.of(
                MDC.get("traceId"),
                request.getRequestURI(),
                new DomainErrorResponse(
                        ApiErrorCode.ACCESS_DENIED.name(),
                        "Access denied",
                        null
                )
        ));

        response.getWriter().write(jsonResponse);
    }
}