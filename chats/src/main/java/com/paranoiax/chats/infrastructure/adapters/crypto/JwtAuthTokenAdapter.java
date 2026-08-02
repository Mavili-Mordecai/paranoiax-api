package com.paranoiax.chats.infrastructure.adapters.crypto;

import com.paranoiax.chats.application.ports.out.AuthTokenPort;
import com.paranoiax.core.application.AccessToken;
import com.paranoiax.core.domain.devices.DeviceId;
import com.paranoiax.core.domain.devices.DeviceType;
import com.paranoiax.core.domain.exceptions.ExpiredException;
import com.paranoiax.core.domain.exceptions.InvalidFormatException;
import com.paranoiax.core.domain.exceptions.InvalidSignatureException;
import com.paranoiax.core.domain.users.UserId;
import com.paranoiax.core.domain.users.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthTokenAdapter implements AuthTokenPort {
    private final PublicKey publicKey;

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