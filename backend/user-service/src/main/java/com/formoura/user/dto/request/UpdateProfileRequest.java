package com.formoura.user.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String fullName;

    @Email
    private String email;

    private String mobile;

    private String profileImage;

}