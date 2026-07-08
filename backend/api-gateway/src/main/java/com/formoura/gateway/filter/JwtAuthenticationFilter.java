package com.formoura.gateway.filter;

import com.formoura.gateway.service.TokenBlacklistService;
import com.formoura.gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Public APIs
        if (path.startsWith("/auth/")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/actuator")) {

            return chain.filter(exchange);
        }

        String header = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token = header.substring(7);

        // Check Redis Blacklist
        return tokenBlacklistService.isBlacklisted(token)
                .flatMap(isBlacklisted -> {

                    if (Boolean.TRUE.equals(isBlacklisted)) {

                        exchange.getResponse()
                                .setStatusCode(HttpStatus.UNAUTHORIZED);

                        return exchange.getResponse().setComplete();
                    }

                    // Validate JWT
                    if (!jwtUtil.validateToken(token)) {

                        exchange.getResponse()
                                .setStatusCode(HttpStatus.UNAUTHORIZED);

                        return exchange.getResponse().setComplete();
                    }

                    return chain.filter(exchange);

                });

    }
}