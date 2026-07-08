package com.formoura.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

 /*   @Column(nullable = false)
    private Long userId;*/

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean revoked;

    public void revoke() {
        this.revoked = true;
    }
}