package com.appointment.booking.service.impl;

import com.appointment.booking.dto.AvailabilityDTO;
import com.appointment.booking.entity.Availability;
import com.appointment.booking.entity.Doctor;
import com.appointment.booking.exception.BadRequestException;
import com.appointment.booking.exception.ConflictException;
import com.appointment.booking.exception.ResourceNotFoundException;
import com.appointment.booking.mapper.AvailabilityMapper;
import com.appointment.booking.repository.AvailabilityRepository;
import com.appointment.booking.repository.DoctorRepository;
import com.appointment.booking.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;
    private final AvailabilityMapper availabilityMapper;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository,
                                  DoctorRepository doctorRepository,
                                  AvailabilityMapper availabilityMapper) {
        this.availabilityRepository = availabilityRepository;
        this.doctorRepository = doctorRepository;
        this.availabilityMapper = availabilityMapper;
    }

    @Override
    public AvailabilityDTO createAvailability(Long doctorId, AvailabilityDTO availabilityDTO) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId));

        if (availabilityDTO.getStartTime().isAfter(availabilityDTO.getEndTime()) ||
            availabilityDTO.getStartTime().equals(availabilityDTO.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        if (availabilityDTO.getDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot create availability for past dates");
        }

        List<Availability> overlappingAvailabilities = availabilityRepository.findOverlappingAvailabilities(
                doctorId, availabilityDTO.getDate(), availabilityDTO.getStartTime(), availabilityDTO.getEndTime());

        if (!overlappingAvailabilities.isEmpty()) {
            throw new ConflictException("Doctor already has overlapping availability during this time slot");
        }

        Availability availability = new Availability(doctor, availabilityDTO.getDate(),
                availabilityDTO.getStartTime(), availabilityDTO.getEndTime());
        Availability savedAvailability = availabilityRepository.save(availability);
        return availabilityMapper.toDTO(savedAvailability);
    }

    @Override
    @Transactional(readOnly = true)
    public AvailabilityDTO getAvailabilityById(Long id) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", id));
        return availabilityMapper.toDTO(availability);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDTO> getAvailabilitiesByDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor", "id", doctorId);
        }
        return availabilityRepository.findByDoctorId(doctorId).stream()
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDTO> getAvailabilitiesByDoctorAndDate(Long doctorId, LocalDate date) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor", "id", doctorId);
        }
        return availabilityRepository.findByDoctorIdAndDate(doctorId, date).stream()
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDTO> getAvailableSlotsByDoctorAndDate(Long doctorId, LocalDate date) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor", "id", doctorId);
        }
        return availabilityRepository.findByDoctorIdAndDateAndIsAvailable(doctorId, date, true).stream()
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDTO> getAvailableSlotsByDate(LocalDate date) {
        return availabilityRepository.findByDateAndIsAvailable(date, true).stream()
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDTO> getUpcomingAvailabilitiesByDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor", "id", doctorId);
        }
        return availabilityRepository.findByDoctorIdAndDateAfter(doctorId, LocalDate.now()).stream()
                .map(availabilityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AvailabilityDTO updateAvailability(Long id, AvailabilityDTO availabilityDTO) {
        Availability existingAvailability = availabilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", id));

        if (availabilityDTO.getStartTime().isAfter(availabilityDTO.getEndTime()) ||
            availabilityDTO.getStartTime().equals(availabilityDTO.getEndTime())) {
            throw new BadRequestException("Start time must be before end time");
        }

        availabilityMapper.updateEntityFromDTO(availabilityDTO, existingAvailability);
        Availability updatedAvailability = availabilityRepository.save(existingAvailability);
        return availabilityMapper.toDTO(updatedAvailability);
    }

    @Override
    public void deleteAvailability(Long id) {
        if (!availabilityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Availability", "id", id);
        }
        availabilityRepository.deleteById(id);
    }

    @Override
    public void markAvailabilityAsBooked(Long availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", availabilityId));
        availability.setIsAvailable(false);
        availabilityRepository.save(availability);
    }

    @Override
    public void markAvailabilityAsAvailable(Long availabilityId) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", availabilityId));
        availability.setIsAvailable(true);
        availabilityRepository.save(availability);
    }
}