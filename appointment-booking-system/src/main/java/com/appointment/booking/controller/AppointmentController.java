package com.appointment.booking.controller;

import com.appointment.booking.dto.ApiResponse;
import com.appointment.booking.dto.AppointmentDTO;
import com.appointment.booking.dto.AppointmentRequest;
import com.appointment.booking.entity.Appointment.AppointmentStatus;
import com.appointment.booking.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointment Management", description = "APIs for managing appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(summary = "Book a new appointment", description = "Books a new appointment with conflict prevention")
    public ResponseEntity<ApiResponse<AppointmentDTO>> bookAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        AppointmentDTO bookedAppointment = appointmentService.bookAppointment(appointmentRequest);
        ApiResponse<AppointmentDTO> response = ApiResponse.success("Appointment booked successfully", bookedAppointment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get appointment by ID", description = "Retrieves appointment details by ID")
    public ResponseEntity<ApiResponse<AppointmentDTO>> getAppointmentById(@Parameter(description = "Appointment ID") @PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        ApiResponse<AppointmentDTO> response = ApiResponse.success("Appointment retrieved successfully", appointment);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get appointments by patient", description = "Retrieves all appointments for a specific patient")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointmentsByPatient(@Parameter(description = "Patient ID") @PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatient(patientId);
        ApiResponse<List<AppointmentDTO>> response = ApiResponse.success("Retrieved " + appointments.size() + " appointments", appointments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get appointments by doctor", description = "Retrieves all appointments for a specific doctor")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointmentsByDoctor(@Parameter(description = "Doctor ID") @PathVariable Long doctorId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        ApiResponse<List<AppointmentDTO>> response = ApiResponse.success("Retrieved " + appointments.size() + " appointments", appointments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}/status/{status}")
    @Operation(summary = "Get patient appointments by status", description = "Retrieves patient appointments filtered by status")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointmentsByPatientAndStatus(
            @Parameter(description = "Patient ID") @PathVariable Long patientId,
            @Parameter(description = "Appointment status") @PathVariable AppointmentStatus status) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByPatientAndStatus(patientId, status);
        ApiResponse<List<AppointmentDTO>> response = ApiResponse.success("Retrieved " + appointments.size() + " " + status + " appointments", appointments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}/status/{status}")
    @Operation(summary = "Get doctor appointments by status", description = "Retrieves doctor appointments filtered by status")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointmentsByDoctorAndStatus(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId,
            @Parameter(description = "Appointment status") @PathVariable AppointmentStatus status) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorAndStatus(doctorId, status);
        ApiResponse<List<AppointmentDTO>> response = ApiResponse.success("Retrieved " + appointments.size() + " " + status + " appointments", appointments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}/upcoming")
    @Operation(summary = "Get upcoming patient appointments", description = "Retrieves upcoming appointments for a patient")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getUpcomingAppointmentsByPatient(@Parameter(description = "Patient ID") @PathVariable Long patientId) {
        List<AppointmentDTO> appointments = appointmentService.getUpcomingAppointmentsByPatient(patientId);
        ApiResponse<List<AppointmentDTO>> response = ApiResponse.success("Retrieved " + appointments.size() + " upcoming appointments", appointments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}/upcoming")
    @Operation(summary = "Get upcoming doctor appointments", description = "Retrieves upcoming appointments for a doctor")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getUpcomingAppointmentsByDoctor(@Parameter(description = "Doctor ID") @PathVariable Long doctorId) {
        List<AppointmentDTO> appointments = appointmentService.getUpcomingAppointmentsByDoctor(doctorId);
        ApiResponse<List<AppointmentDTO>> response = ApiResponse.success("Retrieved " + appointments.size() + " upcoming appointments", appointments);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel appointment", description = "Cancels an existing appointment and frees up the time slot")
    public ResponseEntity<ApiResponse<AppointmentDTO>> cancelAppointment(@Parameter(description = "Appointment ID") @PathVariable Long id) {
        AppointmentDTO canceledAppointment = appointmentService.cancelAppointment(id);
        ApiResponse<AppointmentDTO> response = ApiResponse.success("Appointment canceled successfully", canceledAppointment);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/notes")
    @Operation(summary = "Update appointment notes", description = "Updates the notes for an existing appointment")
    public ResponseEntity<ApiResponse<AppointmentDTO>> updateAppointmentNotes(
            @Parameter(description = "Appointment ID") @PathVariable Long id,
            @Parameter(description = "Notes") @RequestParam String notes) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointmentNotes(id, notes);
        ApiResponse<AppointmentDTO> response = ApiResponse.success("Appointment notes updated successfully", updatedAppointment);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete appointment", description = "Permanently removes an appointment from the system")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@Parameter(description = "Appointment ID") @PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        ApiResponse<Void> response = ApiResponse.success("Appointment deleted successfully");
        return ResponseEntity.ok(response);
    }
}