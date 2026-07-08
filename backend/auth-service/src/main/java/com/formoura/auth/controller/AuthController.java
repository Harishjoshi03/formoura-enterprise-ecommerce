package com.formoura.auth.controller;

import com.formoura.auth.dto.request.LoginRequest;
import com.formoura.auth.dto.request.LogoutRequest;
import com.formoura.auth.dto.request.RefreshTokenRequest;
import com.formoura.auth.dto.request.RegisterRequest;
import com.formoura.auth.dto.response.AuthResponse;
import com.formoura.auth.dto.response.RefreshTokenResponse;
import com.formoura.auth.service.AuthService;

import com.formoura.auth.service.TokenBlacklistService;
import com.formoura.auth.util.JwtUtil;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenBlacklistService tokenBlacklistService;

    private final JwtUtil jwtUtil;

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));

    }
/*
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(authService.refreshToken(request));

    }*/
 /*   @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody LogoutRequest request){

        authService.logout(request.getAccessToken());

        return ResponseEntity.ok("Logout Successful");

    }*/
/*
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        tokenBlacklistService.blacklistToken(
                token,
                jwtUtil.getRemainingValidity(token)
        );

        return ResponseEntity.ok("Logout Successfully");
    }*/

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(

            @RequestHeader("Authorization")
            String authorization){

        authService.logout(authorization);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestBody
            @Valid
            RefreshTokenRequest request){

        return ResponseEntity.ok(
                authService.refreshToken(request));

    }

}