package com.paranoiax.users.infrastructure.config.security;

import com.paranoiax.users.infrastructure.rest.exceptions.ApiErrorCode;
import com.paranoiax.users.infrastructure.rest.exceptions.DomainErrorResponse;
import com.paranoiax.users.infrastructure.rest.exceptions.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(ErrorResponse.of(
                MDC.get("traceId"),
                request.getRequestURI(),
                new DomainErrorResponse(
                        ApiErrorCode.UNAUTHORIZED.name(),
                        "Invalid credentials",
                        null
                )
        ));

        response.getWriter().write(jsonResponse);
    }
}