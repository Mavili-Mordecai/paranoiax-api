package com.paranoiax.users.infrastructure.config.security;

import com.paranoiax.users.domain.models.device.DeviceType;
import com.paranoiax.users.domain.models.user.UserType;
import com.paranoiax.users.infrastructure.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        try {
            Claims claims = jwtService.getClaims(token);

            UUID userId = UUID.fromString(claims.getSubject());
            UserType role = UserType.valueOf(claims.get("user_type", String.class));
            UUID deviceId = UUID.fromString(claims.get("device_id", String.class));
            DeviceType deviceType = DeviceType.valueOf(claims.get("device_type", String.class));

            CustomJwtAuthentication authentication = new CustomJwtAuthentication(userId, role, deviceId, deviceType);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException | IllegalArgumentException e) {

        }

        filterChain.doFilter(request, response);
    }
}