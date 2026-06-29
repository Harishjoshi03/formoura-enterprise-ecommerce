package com.formoura.swagger.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI formouraOpenAPI() {

        return new OpenAPI()

                .info(new Info()
                        .title("Formoura Enterprise API")
                        .version("v1.0")
                        .description("Enterprise E-Commerce APIs")
                        .contact(new Contact()
                                .name("Harish Joshi")
                                .email("admin@formoura.com")))

                .externalDocs(new ExternalDocumentation()
                        .description("API Documentation"));

    }

}