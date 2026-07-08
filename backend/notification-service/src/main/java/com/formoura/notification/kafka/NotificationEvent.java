package com.formoura.notification.kafka;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEvent {

    private Long userId;

    private String email;

    private String subject;

    private String message;

}