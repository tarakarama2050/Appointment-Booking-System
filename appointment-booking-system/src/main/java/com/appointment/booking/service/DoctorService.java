package com.appointment.booking.service;

import com.appointment.booking.dto.DoctorDTO;
import java.util.List;

public interface DoctorService {

    DoctorDTO createDoctor(DoctorDTO doctorDTO);
    DoctorDTO getDoctorById(Long id);
    List<DoctorDTO> getAllDoctors();
    List<DoctorDTO> getDoctorsBySpecialization(String specialization);
    List<DoctorDTO> searchDoctorsByKeyword(String keyword);
    List<String> getAllSpecializations();
    DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO);
    void deleteDoctor(Long id);
}