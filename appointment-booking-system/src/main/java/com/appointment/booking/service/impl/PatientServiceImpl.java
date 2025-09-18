package com.appointment.booking.service.impl;

import com.appointment.booking.dto.PatientDTO;
import com.appointment.booking.entity.Patient;
import com.appointment.booking.exception.ConflictException;
import com.appointment.booking.exception.ResourceNotFoundException;
import com.appointment.booking.mapper.PatientMapper;
import com.appointment.booking.repository.PatientRepository;
import com.appointment.booking.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        if (patientRepository.existsByEmail(patientDTO.getEmail())) {
            throw new ConflictException("Patient with email '" + patientDTO.getEmail() + "' already exists");
        }
        if (patientRepository.existsByPhone(patientDTO.getPhone())) {
            throw new ConflictException("Patient with phone '" + patientDTO.getPhone() + "' already exists");
        }

        Patient patient = patientMapper.toEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
        return patientMapper.toDTO(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDTO getPatientByEmail(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "email", email));
        return patientMapper.toDTO(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));

        patientMapper.updateEntityFromDTO(patientDTO, existingPatient);
        Patient updatedPatient = patientRepository.save(existingPatient);
        return patientMapper.toDTO(updatedPatient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient", "id", id);
        }
        patientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return patientRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhone(String phone) {
        return patientRepository.existsByPhone(phone);
    }
}