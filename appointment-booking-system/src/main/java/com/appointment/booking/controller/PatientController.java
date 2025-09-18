package com.appointment.booking.controller;

import com.appointment.booking.dto.ApiResponse;
import com.appointment.booking.dto.PatientDTO;
import com.appointment.booking.service.PatientService;
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
@RequestMapping("/api/patients")
@Tag(name = "Patient Management", description = "APIs for managing patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Register a new patient", description = "Creates a new patient record in the system")
    public ResponseEntity<ApiResponse<PatientDTO>> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
        ApiResponse<PatientDTO> response = ApiResponse.success("Patient registered successfully", createdPatient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID", description = "Retrieves a patient by their unique identifier")
    public ResponseEntity<ApiResponse<PatientDTO>> getPatientById(@Parameter(description = "Patient ID") @PathVariable Long id) {
        PatientDTO patient = patientService.getPatientById(id);
        ApiResponse<PatientDTO> response = ApiResponse.success("Patient retrieved successfully", patient);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all patients", description = "Retrieves a list of all registered patients")
    public ResponseEntity<ApiResponse<List<PatientDTO>>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        ApiResponse<List<PatientDTO>> response = ApiResponse.success("Retrieved " + patients.size() + " patients", patients);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get patient by email", description = "Retrieves a patient by their email address")
    public ResponseEntity<ApiResponse<PatientDTO>> getPatientByEmail(@Parameter(description = "Patient email") @PathVariable String email) {
        PatientDTO patient = patientService.getPatientByEmail(email);
        ApiResponse<PatientDTO> response = ApiResponse.success("Patient retrieved successfully", patient);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient", description = "Updates an existing patient's information")
    public ResponseEntity<ApiResponse<PatientDTO>> updatePatient(
            @Parameter(description = "Patient ID") @PathVariable Long id,
            @Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO updatedPatient = patientService.updatePatient(id, patientDTO);
        ApiResponse<PatientDTO> response = ApiResponse.success("Patient updated successfully", updatedPatient);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete patient", description = "Removes a patient from the system")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@Parameter(description = "Patient ID") @PathVariable Long id) {
        patientService.deletePatient(id);
        ApiResponse<Void> response = ApiResponse.success("Patient deleted successfully");
        return ResponseEntity.ok(response);
    }
}