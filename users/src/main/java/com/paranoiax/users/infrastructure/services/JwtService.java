package com.paranoiax.users.infrastructure.services;

import com.paranoiax.users.application.ports.out.AuthTokensPort;
import com.paranoiax.users.domain.models.device.Device;
import com.paranoiax.users.domain.models.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtService implements AuthTokensPort {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Value("${jwt.acccess.ttl}")
    private Duration accessTtl;

    @Value("${jwt.refresh.ttl}")
    private Duration refreshTtl;

    @Override
    public String generateAccessToken(User user, Device device) {
        Instant now = Instant.now();

        JwtBuilder builder = Jwts.builder()
                .subject(device.getUserId().value().toString())
                .claim("user_type", user.getType().name())
                .claim("device_id", device.getId().value().toString())
                .claim("device_type", device.getType().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(accessTtl)))
                .signWith(privateKey, Jwts.SIG.RS256);

        return builder.compact();
    }

    @Override
    public String generateRefreshToken(User user, Device device) {
        Instant now = Instant.now();

        JwtBuilder builder = Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(device.getUserId().value().toString())
                .claim("device_id", device.getId().value().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(refreshTtl)))
                .signWith(privateKey, Jwts.SIG.RS256);

        return builder.compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}