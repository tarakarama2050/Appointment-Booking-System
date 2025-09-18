package com.appointment.booking.controller;

import com.appointment.booking.dto.ApiResponse;
import com.appointment.booking.dto.AvailabilityDTO;
import com.appointment.booking.service.AvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Availability Management", description = "APIs for managing doctor availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @Autowired
    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/doctors/{doctorId}/availability")
    @Operation(summary = "Add doctor availability", description = "Declares a new availability time slot for a doctor")
    public ResponseEntity<ApiResponse<AvailabilityDTO>> createAvailability(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId,
            @Valid @RequestBody AvailabilityDTO availabilityDTO) {
        AvailabilityDTO createdAvailability = availabilityService.createAvailability(doctorId, availabilityDTO);
        ApiResponse<AvailabilityDTO> response = ApiResponse.success("Availability created successfully", createdAvailability);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/availability/{id}")
    @Operation(summary = "Get availability by ID", description = "Retrieves availability details by ID")
    public ResponseEntity<ApiResponse<AvailabilityDTO>> getAvailabilityById(@Parameter(description = "Availability ID") @PathVariable Long id) {
        AvailabilityDTO availability = availabilityService.getAvailabilityById(id);
        ApiResponse<AvailabilityDTO> response = ApiResponse.success("Availability retrieved successfully", availability);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/availability/doctor/{doctorId}")
    @Operation(summary = "Get doctor's availability", description = "Retrieves all availability slots for a specific doctor")
    public ResponseEntity<ApiResponse<List<AvailabilityDTO>>> getAvailabilitiesByDoctor(@Parameter(description = "Doctor ID") @PathVariable Long doctorId) {
        List<AvailabilityDTO> availabilities = availabilityService.getAvailabilitiesByDoctor(doctorId);
        ApiResponse<List<AvailabilityDTO>> response = ApiResponse.success("Retrieved " + availabilities.size() + " availability slots", availabilities);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/availability/doctor/{doctorId}/date/{date}")
    @Operation(summary = "Get doctor availability by date", description = "Retrieves doctor's availability for a specific date")
    public ResponseEntity<ApiResponse<List<AvailabilityDTO>>> getAvailabilitiesByDoctorAndDate(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId,
            @Parameter(description = "Date (yyyy-MM-dd)") @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AvailabilityDTO> availabilities = availabilityService.getAvailabilitiesByDoctorAndDate(doctorId, date);
        ApiResponse<List<AvailabilityDTO>> response = ApiResponse.success("Retrieved " + availabilities.size() + " availability slots for date: " + date, availabilities);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/availability/available/{date}")
    @Operation(summary = "Get all available slots by date", description = "Retrieves all available appointment slots for a specific date")
    public ResponseEntity<ApiResponse<List<AvailabilityDTO>>> getAvailableSlotsByDate(
            @Parameter(description = "Date (yyyy-MM-dd)") @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AvailabilityDTO> availableSlots = availabilityService.getAvailableSlotsByDate(date);
        ApiResponse<List<AvailabilityDTO>> response = ApiResponse.success("Retrieved " + availableSlots.size() + " available slots for date: " + date, availableSlots);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/availability/doctor/{doctorId}/available/{date}")
    @Operation(summary = "Get available slots for doctor by date", description = "Retrieves available appointment slots for a specific doctor and date")
    public ResponseEntity<ApiResponse<List<AvailabilityDTO>>> getAvailableSlotsByDoctorAndDate(
            @Parameter(description = "Doctor ID") @PathVariable Long doctorId,
            @Parameter(description = "Date (yyyy-MM-dd)") @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AvailabilityDTO> availableSlots = availabilityService.getAvailableSlotsByDoctorAndDate(doctorId, date);
        ApiResponse<List<AvailabilityDTO>> response = ApiResponse.success("Retrieved " + availableSlots.size() + " available slots", availableSlots);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/availability/{id}")
    @Operation(summary = "Update availability", description = "Updates an existing availability slot")
    public ResponseEntity<ApiResponse<AvailabilityDTO>> updateAvailability(
            @Parameter(description = "Availability ID") @PathVariable Long id,
            @Valid @RequestBody AvailabilityDTO availabilityDTO) {
        AvailabilityDTO updatedAvailability = availabilityService.updateAvailability(id, availabilityDTO);
        ApiResponse<AvailabilityDTO> response = ApiResponse.success("Availability updated successfully", updatedAvailability);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/availability/{id}")
    @Operation(summary = "Delete availability", description = "Removes an availability slot")
    public ResponseEntity<ApiResponse<Void>> deleteAvailability(@Parameter(description = "Availability ID") @PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        ApiResponse<Void> response = ApiResponse.success("Availability deleted successfully");
        return ResponseEntity.ok(response);
    }
}