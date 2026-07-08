/*
package com.formoura.notification.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI notificationOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Formoura Notification Service API")

                        .description("REST APIs for Notification Management")

                        .version("1.0.0")

                        .contact(new Contact()

                                .name("Harish Joshi")

                                .email("support@formoura.com")

                                .url("https://formoura.com"))

                        .license(new License()

                                .name("Apache 2.0")

                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }

*/
/*    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(

                                "/swagger-ui/**",

                                "/swagger-ui.html",

                                "/v3/api-docs/**"

                        ).permitAll()

                        .anyRequest().authenticated());

        return http.build();

    }*//*


}*/
