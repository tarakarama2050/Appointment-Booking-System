package com.appointment.booking.service;

import com.appointment.booking.dto.AvailabilityDTO;
import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {

    AvailabilityDTO createAvailability(Long doctorId, AvailabilityDTO availabilityDTO);
    AvailabilityDTO getAvailabilityById(Long id);
    List<AvailabilityDTO> getAvailabilitiesByDoctor(Long doctorId);
    List<AvailabilityDTO> getAvailabilitiesByDoctorAndDate(Long doctorId, LocalDate date);
    List<AvailabilityDTO> getAvailableSlotsByDoctorAndDate(Long doctorId, LocalDate date);
    List<AvailabilityDTO> getAvailableSlotsByDate(LocalDate date);
    List<AvailabilityDTO> getUpcomingAvailabilitiesByDoctor(Long doctorId);
    AvailabilityDTO updateAvailability(Long id, AvailabilityDTO availabilityDTO);
    void deleteAvailability(Long id);
    void markAvailabilityAsBooked(Long availabilityId);
    void markAvailabilityAsAvailable(Long availabilityId);
}