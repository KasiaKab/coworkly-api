package com.coworkly.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI coworklyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Coworkly API")
                        .description("REST API for coworking booking system")
                        .version("v0.1.0"));
    }
}
