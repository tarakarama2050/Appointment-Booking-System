package com.appointment.booking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Appointment Booking System API")
                        .version("1.0.0")
                        .description("A comprehensive Spring Boot REST API for managing medical appointments with complete CRUD operations, conflict handling, and proper validation.")
                        .contact(new Contact()
                                .name("Appointment Booking System")
                                .url("http://localhost:8080")
                        )
                );
    }
}
