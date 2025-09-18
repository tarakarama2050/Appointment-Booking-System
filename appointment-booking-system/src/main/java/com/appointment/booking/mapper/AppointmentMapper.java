package com.appointment.booking.mapper;

import com.appointment.booking.dto.AppointmentDTO;
import com.appointment.booking.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentDTO toDTO(Appointment appointment) {
        if (appointment == null) return null;

        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setPatientId(appointment.getPatient().getId());
        dto.setPatientName(appointment.getPatient().getName());
        dto.setPatientEmail(appointment.getPatient().getEmail());
        dto.setPatientPhone(appointment.getPatient().getPhone());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorName(appointment.getDoctor().getName());
        dto.setDoctorSpecialization(appointment.getDoctor().getSpecialization());
        dto.setAvailabilityId(appointment.getAvailability().getId());
        dto.setDate(appointment.getDate());
        dto.setStartTime(appointment.getAvailability().getStartTime());
        dto.setEndTime(appointment.getAvailability().getEndTime());
        dto.setStatus(appointment.getStatus());
        dto.setNotes(appointment.getNotes());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setUpdatedAt(appointment.getUpdatedAt());
        return dto;
    }

    public void updateEntityFromDTO(AppointmentDTO dto, Appointment appointment) {
        if (dto == null || appointment == null) return;

        appointment.setDate(dto.getDate());
        appointment.setStatus(dto.getStatus());
        appointment.setNotes(dto.getNotes());
    }
}