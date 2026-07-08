package com.formoura.payment.kafka;

public class KafkaTopics {

    public static final String PAYMENT_SUCCESS =
            "payment-success";

    public static final String PAYMENT_FAILED =
            "payment-failed";

    private KafkaTopics() {
    }
}