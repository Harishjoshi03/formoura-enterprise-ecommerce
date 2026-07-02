package com.formoura.user.dto.response;

import com.formoura.user.entity.Role;
import com.formoura.user.entity.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private Role role;

    private UserStatus status;

    private boolean enabled;

    private boolean emailVerified;

    private boolean phoneVerified;

    private String profileImage;

    private LocalDateTime createdAt;

}