package ru.mrrex.rentcar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
            new Info()
                .title("Rent Car API")
                .version("1.0.0")
                .description("This is a simple Spring Boot RESTful API for a web-based car rental application")
        );
    }
}
