package com.formoura.user.config;

import com.formoura.user.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContext {

    private Long userId;

    private String email;

    private Role role;

}