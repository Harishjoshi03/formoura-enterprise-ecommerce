package com.formoura.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private static final String PREFIX = "BLACKLIST:";

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public Mono<Boolean> blacklistToken(String token,
                                        long remainingValidity) {

        return redisTemplate.opsForValue()

                .set(
                        PREFIX + token,
                        "BLACKLISTED",
                        Duration.ofMillis(remainingValidity)
                );
    }

    public Mono<Boolean> isBlacklisted(String token) {

        return redisTemplate.hasKey(PREFIX + token);
    }

}