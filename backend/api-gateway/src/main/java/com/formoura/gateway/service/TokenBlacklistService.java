package com.formoura.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final ReactiveStringRedisTemplate redisTemplate;

    private static final String PREFIX = "blacklist:";

    public Mono<Boolean> isBlacklisted(String token) {

        return redisTemplate
                .hasKey(PREFIX + token);

    }

    public Mono<Boolean> blacklist(
            String token,
            long expiryMillis) {

        return redisTemplate.opsForValue()

                .set(
                        PREFIX + token,
                        "logout",
                        java.time.Duration.ofMillis(expiryMillis)
                );

    }

}