package com.formoura.notification.kafka;

import com.formoura.event.notification.InvoiceEvent;
import com.formoura.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceConsumer {

    private final EmailService emailService;

    @KafkaListener(
            topics="invoice-topic",
            groupId="notification-group")
    public void consume(
            InvoiceEvent event){

        emailService.sendInvoice(event);

    }

}