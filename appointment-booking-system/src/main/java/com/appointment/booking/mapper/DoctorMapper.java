package com.appointment.booking.mapper;

import com.appointment.booking.dto.DoctorDTO;
import com.appointment.booking.entity.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public DoctorDTO toDTO(Doctor doctor) {
        if (doctor == null) return null;

        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setCreatedAt(doctor.getCreatedAt());
        dto.setUpdatedAt(doctor.getUpdatedAt());
        return dto;
    }

    public Doctor toEntity(DoctorDTO dto) {
        if (dto == null) return null;

        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        return doctor;
    }

    public void updateEntityFromDTO(DoctorDTO dto, Doctor doctor) {
        if (dto == null || doctor == null) return;

        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
    }
}