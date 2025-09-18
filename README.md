# Appointment-Booking-System
In this Project I created a backend logic to book a appointment for a Doctor . By Preventing  double booking with availability checking.

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
