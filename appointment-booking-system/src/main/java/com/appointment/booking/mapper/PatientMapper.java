package com.appointment.booking.mapper;

import com.appointment.booking.dto.PatientDTO;
import com.appointment.booking.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientDTO toDTO(Patient patient) {
        if (patient == null) return null;

        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setPhone(patient.getPhone());
        dto.setCreatedAt(patient.getCreatedAt());
        dto.setUpdatedAt(patient.getUpdatedAt());
        return dto;
    }

    public Patient toEntity(PatientDTO dto) {
        if (dto == null) return null;

        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setPhone(dto.getPhone());
        return patient;
    }

    public void updateEntityFromDTO(PatientDTO dto, Patient patient) {
        if (dto == null || patient == null) return;

        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        patient.setPhone(dto.getPhone());
    }
}