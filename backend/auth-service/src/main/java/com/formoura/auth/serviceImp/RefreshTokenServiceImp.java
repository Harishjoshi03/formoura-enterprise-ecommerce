package com.formoura.auth.serviceImp;

import com.formoura.auth.entity.RefreshToken;
import com.formoura.auth.entity.User;
import com.formoura.auth.repository.RefreshTokenRepository;

import com.formoura.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImp implements RefreshTokenService {
    private final RefreshTokenRepository repository;

    public RefreshToken verify(String token){

        RefreshToken refreshToken =
                repository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Refresh Token Not Found"));

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){

            repository.delete(refreshToken);

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

        return repository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken =
                repository.findByToken(token)
                        .orElseThrow(() ->
                                new RuntimeException("Refresh Token Not Found"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            repository.delete(refreshToken);

            throw new RuntimeException("Refresh Token Expired");
        }

        return refreshToken;
    }

    @Override
    public void deleteRefreshToken(User user) {

        repository.deleteByUser(user);

    }
}