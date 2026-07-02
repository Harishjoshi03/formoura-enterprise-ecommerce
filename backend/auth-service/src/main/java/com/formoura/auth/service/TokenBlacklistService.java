package com.formoura.auth.service;

public interface TokenBlacklistService {

    void blacklistToken(String token, long validity);

    boolean isBlacklisted(String token);

}