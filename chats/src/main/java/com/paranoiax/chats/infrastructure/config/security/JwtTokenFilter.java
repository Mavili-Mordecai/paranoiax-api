package com.paranoiax.chats.infrastructure.config.security;

import com.paranoiax.chats.infrastructure.adapters.crypto.JwtAuthTokenAdapter;
import com.paranoiax.core.application.AccessToken;
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

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtAuthTokenAdapter jwtAuthTokensAdapter;

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
            AccessToken accessToken = jwtAuthTokensAdapter.parseAccessToken(token);
            CustomJwtAuthentication authentication = new CustomJwtAuthentication(
                    accessToken.getUserId().value(),
                    accessToken.getType(),
                    accessToken.getDeviceId().value(),
                    accessToken.getDeviceType()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException | IllegalArgumentException e) {

        }

        filterChain.doFilter(request, response);
    }
}