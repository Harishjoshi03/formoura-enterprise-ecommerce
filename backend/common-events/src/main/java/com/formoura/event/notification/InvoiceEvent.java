package com.formoura.event.notification;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEvent {

    private Long orderId;

    private Long userId;

    private String email;

    private String invoicePath;

}