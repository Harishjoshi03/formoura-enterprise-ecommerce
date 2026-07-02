package com.formoura.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey key() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));

    }

    public boolean validateToken(String token) {

        try {

            Jwts.parser()

                    .verifyWith(key())

                    .build()

                    .parseSignedClaims(token);

            return true;

        } catch (Exception ex) {

            return false;

        }

    }

    public String extractUsername(String token) {

        Claims claims = Jwts.parser()

                .verifyWith(key())

                .build()

                .parseSignedClaims(token)

                .getPayload();

        return claims.getSubject();

    }

}