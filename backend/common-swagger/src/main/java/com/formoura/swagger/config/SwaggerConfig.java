package com.formoura.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SwaggerConfig {

   // private final JwtAuthenticationFilter jwtAuthenticationFilter;

   /* @Bean
    public OpenAPI formouraOpenAPI() {

        System.out.println("******** Swagger Config Loaded ********");
        return new OpenAPI()

                .info(new Info()

                        .title("Formoura Enterprise APIs")

                        .version("1.0.0")

                        .description("Enterprise E-Commerce APIs")

                        .contact(new Contact()

                                .name("Harish Joshi")

                                .email("admin@formoura.com")))

                .externalDocs(

                        new ExternalDocumentation()

                                .description("Formoura Documentation"));

    }*/
   @Bean
   public OpenAPI formouraOpenAPI() {

       final String securitySchemeName = "Bearer Authentication";

       return new OpenAPI()

               .info(new Info()

                       .title("Formoura Enterprise APIs")

                       .version("1.0.0")

                       .description("Enterprise E-Commerce APIs")

                       .contact(new Contact()

                               .name("Harish Joshi")

                               .email("admin@formoura.com")))

               .addSecurityItem(
                       new SecurityRequirement()
                               .addList(securitySchemeName))

               .components(

                       new Components()

                               .addSecuritySchemes(

                                       securitySchemeName,

                                       new SecurityScheme()

                                               .name(securitySchemeName)

                                               .type(SecurityScheme.Type.HTTP)

                                               .scheme("bearer")

                                               .bearerFormat("JWT")

                               )

               );

   }
  /*  @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http

                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)

                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)

                .authorizeExchange(exchange -> exchange

                        // Public APIs
                        .pathMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/auth/refresh",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Everything else requires JWT
                        .anyExchange().authenticated()

                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        SecurityWebFiltersOrder.AUTHENTICATION
                )

                .build();
    }
*/
}