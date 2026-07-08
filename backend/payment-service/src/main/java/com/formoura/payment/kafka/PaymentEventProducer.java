package com.formoura.payment.kafka;

import com.formoura.event.notification.InvoiceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void publishSuccess(Object event){

        kafkaTemplate.send(
                KafkaTopics.PAYMENT_SUCCESS,
                event);

    }

    public void publishFailed(Object event){

        kafkaTemplate.send(
                KafkaTopics.PAYMENT_FAILED,
                event);

    }

    public void publishInvoice(
            InvoiceEvent event){

        kafkaTemplate.send(
                "invoice-topic",
                event);

    }

}