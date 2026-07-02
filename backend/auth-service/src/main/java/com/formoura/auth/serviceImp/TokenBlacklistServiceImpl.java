package com.formoura.auth.serviceImp;

import com.formoura.auth.service.TokenBlacklistService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final RedisTemplate<String,String> redisTemplate;

    @Override
    public void blacklistToken(String token,long expiration){

        redisTemplate.opsForValue().set(
                token,
                "BLACKLISTED",
                expiration,
                TimeUnit.MILLISECONDS);

    }

    @Override
    public boolean isBlacklisted(String token){

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(token));

    }

}