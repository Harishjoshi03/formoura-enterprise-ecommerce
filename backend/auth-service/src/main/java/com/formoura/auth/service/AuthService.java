package com.formoura.auth.service;


import com.formoura.auth.dto.request.LoginRequest;
import com.formoura.auth.dto.request.RefreshTokenRequest;
import com.formoura.auth.dto.request.RegisterRequest;
import com.formoura.auth.dto.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void logout(String token);
}