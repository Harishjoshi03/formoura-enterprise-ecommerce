package com.formoura.auth.service;

import com.formoura.auth.dto.request.RefreshTokenRequest;
import com.formoura.auth.dto.response.RefreshTokenResponse;
import com.formoura.auth.entity.RefreshToken;
import com.formoura.auth.entity.User;
import org.springframework.stereotype.Service;


public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void deleteRefreshToken(User user);

    void logout(Long userId);

    RefreshTokenResponse refreshToken(
            RefreshTokenRequest request);
}