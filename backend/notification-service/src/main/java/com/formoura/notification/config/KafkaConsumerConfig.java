package com.formoura.notification.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(
            KafkaProperties properties) {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                properties.getBootstrapServers());

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "notification-group");

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        config.put(
                JsonDeserializer.TRUSTED_PACKAGES,
                "*");

        config.put(
                JsonDeserializer.USE_TYPE_INFO_HEADERS,
                false);

        config.put(
                JsonDeserializer.VALUE_DEFAULT_TYPE,
                "com.formoura.event.user.UserRegisteredEvent");

        return new DefaultKafkaConsumerFactory<>(config);

    }

}