package com.formoura.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String phone;

    private String profileImage;

}