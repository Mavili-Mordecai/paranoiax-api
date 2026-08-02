package com.paranoiax.users.infrastructure.adapters.crypto;

import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.devices.DeviceType;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core.domain.users.UserType;
import com.paranoiax.core.application.AccessToken;
import com.paranoiax.core.application.RefreshToken;
import com.paranoiax.users.application.ports.in.auth.TokenPair;
import com.paranoiax.users.application.ports.out.AuthTokenPort;
import com.paranoiax.core.domain.exceptions.ExpiredException;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;
import com.paranoiax.core.domain.exceptions.InvalidSignatureException;
import com.paranoiax.users.domain.models.device.Device;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
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
public class JwtAuthTokenAdapter implements AuthTokenPort {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Value("${jwt.acccess.ttl}")
    private Duration accessTtl;

    @Value("${jwt.refresh.ttl}")
    private Duration refreshTtl;

    @Override
    public TokenPair generateTokenPair(Device device) {
        Instant now = Instant.now();
        return new TokenPair(
                generateAccessToken(device, now),
                generateRefreshToken(device, now)
        );
    }

    @Override
    public AccessToken parseAccessToken(String token) {
        Claims claims = getClaims(token);
        return new AccessToken(
                new UserId(UUID.fromString(claims.getSubject())),
                UserType.valueOf(claims.get("user_type", String.class)),
                new DeviceId(UUID.fromString(claims.get("device_id", String.class))),
                DeviceType.valueOf(claims.get("device_type", String.class)),
                claims.getExpiration().toInstant()
        );
    }

    @Override
    public RefreshToken parseRefreshToken(String token) {
        Claims claims = getClaims(token);
        return new RefreshToken(
                UUID.fromString(claims.getId()),
                new UserId(UUID.fromString(claims.getSubject())),
                new DeviceId(UUID.fromString(claims.get("device_id", String.class))),
                claims.getExpiration().toInstant()
        );
    }

    private String generateAccessToken(Device device, Instant now) {
        return Jwts.builder()
                .subject(device.getUserId().value().toString())
                .claim("user_type", UserType.USER.name())
                .claim("device_id", device.getId().value().toString())
                .claim("device_type", device.getType().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(accessTtl)))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    private String generateRefreshToken(Device device, Instant now) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(device.getUserId().value().toString())
                .claim("device_id", device.getId().value().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(refreshTtl)))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            throw new ExpiredException("Token");
        } catch (SignatureException ex) {
            throw new InvalidSignatureException("Token");
        } catch (IllegalArgumentException | JwtException ex) {
            throw new InvalidFormatException("Token");
        }
    }
}