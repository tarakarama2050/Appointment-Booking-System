package com.appointment.booking.mapper;

import com.appointment.booking.dto.AvailabilityDTO;
import com.appointment.booking.entity.Availability;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityMapper {

    public AvailabilityDTO toDTO(Availability availability) {
        if (availability == null) return null;

        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setId(availability.getId());
        dto.setDoctorId(availability.getDoctor().getId());
        dto.setDoctorName(availability.getDoctor().getName());
        dto.setDate(availability.getDate());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());
        dto.setIsAvailable(availability.getIsAvailable());
        dto.setCreatedAt(availability.getCreatedAt());
        dto.setUpdatedAt(availability.getUpdatedAt());
        return dto;
    }

    public void updateEntityFromDTO(AvailabilityDTO dto, Availability availability) {
        if (dto == null || availability == null) return;

        availability.setDate(dto.getDate());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setIsAvailable(dto.getIsAvailable());
    }
}