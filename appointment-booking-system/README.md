Appointment Booking System

A comprehensive Spring Boot REST API application for managing medical appointments with complete CRUD operations, conflict handling, and Swagger documentation.



Core Features

✅ **Patient Registration & Management**
✅ **Doctor Registration & Specialization Management**  
✅ **Doctor Availability Time Slot Declaration**
✅ **Appointment Booking with Conflict Prevention**
✅ **Appointment Viewing & Cancellation**
✅ **Complete REST API with Swagger Documentation**
✅ **H2 In-Memory Database with Auto Schema Generation**

API Endpoints Overview

Patient Management
- `POST /api/patients` - Register new patient
- `GET /api/patients` - Get all patients
- `GET /api/patients/{id}` - Get patient by ID

Doctor Management  
- `POST /api/doctors` - Register new doctor
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/specialization/{spec}` - Get doctors by specialization

Availability Management
- `POST /api/doctors/{id}/availability` - Declare doctor availability
- `GET /api/availability/doctor/{id}` - Get doctor availability

Appointment Management
- `POST /api/appointments` - Book appointment
- `GET /api/appointments/patient/{id}` - Get patient appointments
- `PUT /api/appointments/{id}/cancel` - Cancel appointment

Test with Sample Data

Use Swagger UI for interactive testing with these sample payloads:

**Register Patient:**
```json
{
  "name": "John Doe",
  "email": "john@email.com", 
  "phone": "9876543210"
}
```

**Register Doctor:**
```json
{
  "name": "Dr. Smith",
  "specialization": "Cardiology"
}
```

**Add Availability:**
```json
{
  "date": "2025-09-20",
  "startTime": "10:00",
  "endTime": "12:00"
}
```

**Book Appointment:**
```json
{
  "patientId": 1,
  "doctorId": 1, 
  "availabilityId": 1,
  "date": "2025-09-20"
}
```

Tech Stack

- **Java 17**
- **Spring Boot 3.1.5**
- **Hibernate/JPA**
- **H2 Database** 
- **Swagger OpenAPI 3**
- **Maven**

Architecture

```
📁 src/main/java/com/appointment/booking/
├── 📂 controller/     # REST API Controllers
├── 📂 dto/           # Data Transfer Objects  
├── 📂 entity/        # JPA Entities
├── 📂 exception/     # Custom Exception Classes
├── 📂 mapper/        # Entity-DTO Mappers
├── 📂 repository/    # Data Access Repositories
├── 📂 service/       # Business Logic Services
└── 📂 config/        # Configuration Classes
```

Key Implementation Highlights

- **Conflict Prevention**: Prevents double booking with availability checking
- **Validation**: Comprehensive input validation with custom messages  
- **Exception Handling**: Global exception handler with proper HTTP codes
- **Clean Architecture**: Layered design with separation of concerns
- **API Documentation**: Complete Swagger/OpenAPI documentation
- **Transaction Management**: Proper transaction boundaries


