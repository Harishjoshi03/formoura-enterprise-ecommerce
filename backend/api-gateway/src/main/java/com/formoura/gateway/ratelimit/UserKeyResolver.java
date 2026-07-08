package com.formoura.gateway.ratelimit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class UserKeyResolver {

    @Bean
    public KeyResolver userKey(){

        return exchange -> {

            String authorization =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst("Authorization");

            if (authorization != null) {

                return Mono.just(authorization);

            }

            return Mono.just(
                    exchange.getRequest()
                            .getRemoteAddress()
                            .getAddress()
                            .getHostAddress());

        };

    }

}