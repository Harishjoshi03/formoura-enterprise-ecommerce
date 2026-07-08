/*
package com.formoura.auth.controller;

import com.formoura.auth.dto.request.RefreshTokenRequest;
import com.formoura.auth.dto.response.RefreshTokenResponse;
import com.formoura.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                refreshTokenService.refreshToken(request)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestParam Long userId) {

        refreshTokenService.logout(userId);

        return ResponseEntity.ok("Logout Successfully");
    }

}*/
