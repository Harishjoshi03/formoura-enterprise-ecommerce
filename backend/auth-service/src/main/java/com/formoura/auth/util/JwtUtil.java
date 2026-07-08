package com.formoura.auth.util;

import com.formoura.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        return resolver.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {

        Date expiry = extractClaim(token, Claims::getExpiration);

        return expiry.before(new Date());
    }

  /*  public boolean validateToken(String token) {

        return !isTokenExpired(token);
    }*/

    public long getRemainingValidity(String token){

        Date expiration =
                extractClaim(token, Claims::getExpiration);

        return expiration.getTime()
                - System.currentTimeMillis();

    }
  /*  public String generateAccessToken(User user) {

        return Jwts.builder()

                .subject(user.getId().toString())

                .claim("email", user.getEmail())

                .claim("role", user.getRole().name())

                .issuedAt(new Date())

                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))

                .signWith(getSigningKey())

                .compact();

    }*/

    public String generateRefreshToken(User user) {

        return Jwts.builder()

                .subject(user.getId().toString())

                .issuedAt(new Date())

                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))

                .signWith(getSigningKey())

                .compact();

    }

   /* public Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }
*/
    /*public boolean validateToken(String token) {

        try {

            extractAllClaims(token);

            return true;

        } catch (Exception ex) {

            return false;

        }

    }*/
/*
    public Long extractUserId(String token) {

        return Long.valueOf(
                extractClaim(token, Claims::getSubject)
        );

    }*/

    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);

    }

    public String generateAccessToken(User user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("userId", user.getId());

        claims.put("email", user.getEmail());

        claims.put("role", user.getRole().name());

        return Jwts.builder()

                .claims(claims)

                .subject(user.getEmail())

                .issuedAt(new Date())

                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))

                .signWith(getSigningKey())

                .compact();

    }
    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);

    }

    public String extractEmail(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    public Long extractUserId(String token) {

        Claims claims = extractAllClaims(token);

        Object value = claims.get("userId");

        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }

        if (value instanceof Long) {
            return (Long) value;
        }

        return Long.parseLong(value.toString());
    }

    public boolean validateToken(String token) {

        try {

            extractAllClaims(token);

            return true;

        } catch (ExpiredJwtException ex) {

            return false;

        } catch (JwtException ex) {

            return false;

        } catch (Exception ex) {

            return false;

        }

    }
    public String generateRefreshToken(Long userId) {

        return Jwts.builder()

                .subject(String.valueOf(userId))

                .issuedAt(new Date())

                .expiration(new Date(
                        System.currentTimeMillis()
                                + refreshTokenExpiration))

                .signWith(getSigningKey())

                .compact();

    }
}