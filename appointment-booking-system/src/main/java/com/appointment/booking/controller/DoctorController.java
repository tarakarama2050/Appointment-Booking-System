package com.appointment.booking.controller;

import com.appointment.booking.dto.ApiResponse;
import com.appointment.booking.dto.DoctorDTO;
import com.appointment.booking.service.DoctorService;
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
@RequestMapping("/api/doctors")
@Tag(name = "Doctor Management", description = "APIs for managing doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Operation(summary = "Register a new doctor", description = "Creates a new doctor record in the system")
    public ResponseEntity<ApiResponse<DoctorDTO>> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO createdDoctor = doctorService.createDoctor(doctorDTO);
        ApiResponse<DoctorDTO> response = ApiResponse.success("Doctor registered successfully", createdDoctor);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get doctor by ID", description = "Retrieves a doctor by their unique identifier")
    public ResponseEntity<ApiResponse<DoctorDTO>> getDoctorById(@Parameter(description = "Doctor ID") @PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctorById(id);
        ApiResponse<DoctorDTO> response = ApiResponse.success("Doctor retrieved successfully", doctor);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all doctors", description = "Retrieves a list of all registered doctors")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        ApiResponse<List<DoctorDTO>> response = ApiResponse.success("Retrieved " + doctors.size() + " doctors", doctors);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/specialization/{specialization}")
    @Operation(summary = "Get doctors by specialization", description = "Retrieves all doctors with a specific specialization")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> getDoctorsBySpecialization(@Parameter(description = "Doctor specialization") @PathVariable String specialization) {
        List<DoctorDTO> doctors = doctorService.getDoctorsBySpecialization(specialization);
        ApiResponse<List<DoctorDTO>> response = ApiResponse.success("Retrieved " + doctors.size() + " doctors with specialization: " + specialization, doctors);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search doctors", description = "Search doctors by name or specialization keyword")
    public ResponseEntity<ApiResponse<List<DoctorDTO>>> searchDoctors(@Parameter(description = "Search keyword") @RequestParam String keyword) {
        List<DoctorDTO> doctors = doctorService.searchDoctorsByKeyword(keyword);
        ApiResponse<List<DoctorDTO>> response = ApiResponse.success("Found " + doctors.size() + " doctors matching keyword: " + keyword, doctors);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/specializations")
    @Operation(summary = "Get all specializations", description = "Retrieves a list of all available doctor specializations")
    public ResponseEntity<ApiResponse<List<String>>> getAllSpecializations() {
        List<String> specializations = doctorService.getAllSpecializations();
        ApiResponse<List<String>> response = ApiResponse.success("Retrieved " + specializations.size() + " specializations", specializations);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update doctor", description = "Updates an existing doctor's information")
    public ResponseEntity<ApiResponse<DoctorDTO>> updateDoctor(
            @Parameter(description = "Doctor ID") @PathVariable Long id,
            @Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(id, doctorDTO);
        ApiResponse<DoctorDTO> response = ApiResponse.success("Doctor updated successfully", updatedDoctor);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete doctor", description = "Removes a doctor from the system")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(@Parameter(description = "Doctor ID") @PathVariable Long id) {
        doctorService.deleteDoctor(id);
        ApiResponse<Void> response = ApiResponse.success("Doctor deleted successfully");
        return ResponseEntity.ok(response);
    }
}