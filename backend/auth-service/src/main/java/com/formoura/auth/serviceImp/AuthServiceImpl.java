package com.formoura.auth.serviceImp;


import com.formoura.auth.dto.request.LoginRequest;
import com.formoura.auth.dto.request.RefreshTokenRequest;
import com.formoura.auth.dto.request.RegisterRequest;
import com.formoura.auth.dto.response.AuthResponse;
import com.formoura.auth.dto.response.RefreshTokenResponse;
import com.formoura.auth.entity.RefreshToken;
import com.formoura.auth.entity.Role;
import com.formoura.auth.entity.User;
import com.formoura.auth.repository.RefreshTokenRepository;
import com.formoura.auth.repository.UserRepository;
import com.formoura.auth.service.AuthService;
import com.formoura.auth.service.JwtService;
import com.formoura.auth.service.RefreshTokenService;

import com.formoura.auth.service.TokenBlacklistService;
import com.formoura.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final TokenBlacklistService tokenBlacklistService;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        repository.save(user);

        String accessToken =
                jwtService.generateToken(user.getEmail());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken());

    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String accessToken =
                jwtService.generateToken(user.getEmail());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken());

    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken());

        String accessToken =
                jwtService.generateToken(
                        refreshToken.getUser().getEmail());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken());

    }

    @Override
    public void logout(String token){

        String username=jwtService.extractUsername(token);

        User user=repository.findByEmail(username)
                .orElseThrow();

        refreshTokenService.deleteRefreshToken(user);

        long expiry=jwtService.getRemainingValidity(token);

        tokenBlacklistService.blacklistToken(token,expiry);

    }


}