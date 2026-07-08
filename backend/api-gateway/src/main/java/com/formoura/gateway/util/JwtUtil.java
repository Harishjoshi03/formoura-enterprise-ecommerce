package com.formoura.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
    }

    // =========================
    // Generate Access Token
    // =========================

    public String generateAccessToken(
            Long userId,
            String email,
            String role) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("role", role);

        return Jwts.builder()

                .claims(claims)

                .subject(email)

                .issuedAt(new Date())

                .expiration(new Date(
                        System.currentTimeMillis()
                                + accessTokenExpiration))

                .signWith(signingKey)

                .compact();
    }

    // =========================
    // Generate Refresh Token
    // =========================

    public String generateRefreshToken(Long userId) {

        return Jwts.builder()

                .subject(String.valueOf(userId))

                .issuedAt(new Date())

                .expiration(new Date(
                        System.currentTimeMillis()
                                + refreshTokenExpiration))

                .signWith(signingKey)

                .compact();
    }

    // =========================
    // Extract Claims
    // =========================

    public Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith(signingKey)

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        return resolver.apply(extractAllClaims(token));
    }

    // =========================
    // Extract Username
    // =========================

    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject);
    }

    // =========================
    // Extract Email
    // =========================

    public String extractEmail(String token) {

        return extractClaim(
                token,
                Claims::getSubject);
    }

    // =========================
    // Extract Role
    // =========================

    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    // =========================
    // Extract UserId
    // =========================

    public Long extractUserId(String token) {

        Object value = extractAllClaims(token)
                .get("userId");

        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }

        if (value instanceof Long) {
            return (Long) value;
        }

        return Long.parseLong(value.toString());
    }

    // =========================
    // Expiration
    // =========================

    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    public long getRemainingValidity(String token) {

        return extractExpiration(token)
                .getTime()
                - System.currentTimeMillis();
    }

    // =========================
    // Validate Token
    // =========================

    public boolean validateToken(String token) {

        try {

            extractAllClaims(token);

            return !isTokenExpired(token);

        } catch (ExpiredJwtException ex) {

            return false;

        } catch (JwtException ex) {

            return false;

        } catch (IllegalArgumentException ex) {

            return false;

        }
    }
}