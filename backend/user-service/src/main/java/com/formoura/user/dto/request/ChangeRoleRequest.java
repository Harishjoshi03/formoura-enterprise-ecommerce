package com.formoura.user.dto.request;

import com.formoura.user.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeRoleRequest {

    @NotNull
    private Role role;

}