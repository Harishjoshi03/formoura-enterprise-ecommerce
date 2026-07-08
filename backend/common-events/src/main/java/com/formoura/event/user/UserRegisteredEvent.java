package com.formoura.event.user;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent {

    private Long userId;

    private String fullName;

    private String email;

    private String role;

    private LocalDateTime registeredAt;

}