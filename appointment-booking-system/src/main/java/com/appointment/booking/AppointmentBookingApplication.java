package com.appointment.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppointmentBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentBookingApplication.class, args);
        System.out.println("\n=== 🏥 Appointment Booking System Started Successfully ===");
        System.out.println("🌐 Swagger UI: http://localhost:8080/swagger-ui.html");  
        System.out.println("🗄️ H2 Console: http://localhost:8080/h2-console");
        System.out.println("📚 API Docs: http://localhost:8080/api-docs");
        System.out.println("===============================================\n");
    }
}