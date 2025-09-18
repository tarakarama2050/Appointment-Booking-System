package com.appointment.booking.service;

import com.appointment.booking.dto.PatientDTO;
import java.util.List;

public interface PatientService {

    PatientDTO createPatient(PatientDTO patientDTO);
    PatientDTO getPatientById(Long id);
    PatientDTO getPatientByEmail(String email);
    List<PatientDTO> getAllPatients();
    PatientDTO updatePatient(Long id, PatientDTO patientDTO);
    void deletePatient(Long id);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}