package com.formoura.gateway.filter;

import com.formoura.gateway.service.TokenBlacklistService;
import com.formoura.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    private final TokenBlacklistService tokenBlacklistService;

    private static final List<String> PUBLIC_APIS = List.of(
            "/auth/login",
            "/auth/register",
            "/auth/refresh",
            "/swagger-ui",
            "/v3/api-docs",
            "/actuator",
            "/eureka"
    );

    private static final Map<String, List<String>> ROLE_ACCESS = Map.of(

            "/admin/", List.of("ADMIN"),

            "/seller/", List.of("SELLER"),

            "/vendor/", List.of("VENDOR"),

            "/user/", List.of("USER", "ADMIN")

    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest()
                .getURI()
                .getPath();

        // Skip Public APIs
        if (isPublic(path)) {
            return chain.filter(exchange);
        }

        // Read Authorization Header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Authorization header missing");
        }

        String token = authHeader.substring(7);

        try {

            // Validate Token
            if (!jwtUtil.validateToken(token)) {
                return unauthorized(exchange, "Invalid Token");
            }

            // Extract Claims
            Claims claims = jwtUtil.extractAllClaims(token);

            String userId = claims.getSubject();

            String email = claims.get("email", String.class);

            String role = claims.get("role", String.class);

            if (!hasAccess(path, role)) {

                return forbidden(exchange);

            }

            // Forward User Information
            ServerHttpRequest request = exchange.getRequest()
                    .mutate()

                    .header("X-User-Id", claims.get("userId").toString())

                    .header("X-Email", claims.get("email", String.class))

                    .header("X-Role", claims.get("role", String.class))

                    .build();

            return chain.filter(
                    exchange.mutate()
                            .request(request)
                            .build()
            );
        } catch (Exception ex) {

            return unauthorized(exchange, "Invalid or Expired Token");

        }

    }

    private boolean isPublic(String path) {

        return PUBLIC_APIS.stream()
                .anyMatch(path::startsWith);

    }

    private Mono<Void> unauthorized(ServerWebExchange exchange,
                                    String message) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        exchange.getResponse()
                .getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                  "status":401,
                  "error":"Unauthorized",
                  "message":"%s"
                }
                """.formatted(message);

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(body.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse()
                .writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1;
    }


    private boolean hasAccess(String path, String role) {

        for (Map.Entry<String, List<String>> entry : ROLE_ACCESS.entrySet()) {

            if (path.startsWith(entry.getKey())) {

                return entry.getValue().contains(role);

            }

        }

        return true;

    }
    private Mono<Void> forbidden(ServerWebExchange exchange){

        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);

        exchange.getResponse().getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        String body="""
        {
            "status":403,
            "message":"Access Denied"
        }
        """;

        DataBuffer buffer=
                exchange.getResponse()
                        .bufferFactory()
                        .wrap(body.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse()
                .writeWith(Mono.just(buffer));

    }
}