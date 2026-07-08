package com.formoura.auth.serviceImp;

import com.formoura.auth.dto.request.RefreshTokenRequest;
import com.formoura.auth.dto.response.RefreshTokenResponse;
import com.formoura.auth.entity.RefreshToken;
import com.formoura.auth.entity.User;
import com.formoura.auth.repository.RefreshTokenRepository;

import com.formoura.auth.repository.UserRepository;
import com.formoura.auth.service.RefreshTokenService;
import com.formoura.auth.util.JwtUtil;
import com.formoura.exception.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImp implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public RefreshToken verify(String token){

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Refresh Token Not Found"));

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Refresh Token Expired");
        }

        return refreshToken;

    }

    @Override
    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Refresh Token Not Found"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Refresh Token Expired");
        }

        return refreshToken;
    }

    @Override
    public void deleteRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);

    }

    @Override
    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository

                .findByToken(request.getRefreshToken())

                .orElseThrow(() ->
                        new BusinessException("Invalid Refresh Token"));

        if (refreshToken.isRevoked()) {

            throw new BusinessException(
                    "Refresh Token Revoked");

        }

        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new BusinessException(
                    "Refresh Token Expired");

        }

        User user = userRepository.findById(
                        refreshToken.getUser().getId())

                .orElseThrow(() ->
                        new BusinessException("User Not Found"));

        // Rotate Refresh Token
        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);

        String newAccessToken =
                jwtUtil.generateAccessToken(user);

        String newRefreshToken =
                jwtUtil.generateRefreshToken(user);

        RefreshToken token = RefreshToken.builder()

                .token(newRefreshToken)

                .user(user)

                .expiryDate(
                        LocalDateTime.now().plusDays(7))

                .revoked(false)

                .build();

        refreshTokenRepository.save(token);

        return RefreshTokenResponse.builder()

                .accessToken(newAccessToken)

                .refreshToken(newRefreshToken)

                .build();

    }

    @Override
    public void logout(Long userId) {

        refreshTokenRepository.deleteByUserId(userId);

    }
}