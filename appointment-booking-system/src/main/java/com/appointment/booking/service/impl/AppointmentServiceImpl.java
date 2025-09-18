package com.appointment.booking.service.impl;

import com.appointment.booking.dto.AppointmentDTO;
import com.appointment.booking.dto.AppointmentRequest;
import com.appointment.booking.entity.Appointment;
import com.appointment.booking.entity.Appointment.AppointmentStatus;
import com.appointment.booking.entity.Availability;
import com.appointment.booking.entity.Doctor;
import com.appointment.booking.entity.Patient;
import com.appointment.booking.exception.BadRequestException;
import com.appointment.booking.exception.ConflictException;
import com.appointment.booking.exception.ResourceNotFoundException;
import com.appointment.booking.mapper.AppointmentMapper;
import com.appointment.booking.repository.AppointmentRepository;
import com.appointment.booking.repository.AvailabilityRepository;
import com.appointment.booking.repository.DoctorRepository;
import com.appointment.booking.repository.PatientRepository;
import com.appointment.booking.service.AppointmentService;
import com.appointment.booking.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AvailabilityRepository availabilityRepository;
    private final AppointmentMapper appointmentMapper;
    private final AvailabilityService availabilityService;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                 PatientRepository patientRepository,
                                 DoctorRepository doctorRepository,
                                 AvailabilityRepository availabilityRepository,
                                 AppointmentMapper appointmentMapper,
                                 AvailabilityService availabilityService) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.availabilityRepository = availabilityRepository;
        this.appointmentMapper = appointmentMapper;
        this.availabilityService = availabilityService;
    }

    @Override
    public AppointmentDTO bookAppointment(AppointmentRequest appointmentRequest) {
        // Validate patient exists
        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", appointmentRequest.getPatientId()));

        // Validate doctor exists
        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", appointmentRequest.getDoctorId()));

        // Validate availability exists and is available
        Availability availability = availabilityRepository.findByIdAndIsAvailable(appointmentRequest.getAvailabilityId(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Available slot", "id", appointmentRequest.getAvailabilityId()));

        // Validate that the availability belongs to the specified doctor
        if (!availability.getDoctor().getId().equals(appointmentRequest.getDoctorId())) {
            throw new BadRequestException("The selected availability slot does not belong to the specified doctor");
        }

        // Check for past dates
        if (appointmentRequest.getDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot book appointment for past dates");
        }

        // Check for double booking (conflict handling)
        Optional<Appointment> existingAppointment = appointmentRepository
                .findActiveAppointmentByAvailabilityAndDate(appointmentRequest.getAvailabilityId(), appointmentRequest.getDate());

        if (existingAppointment.isPresent()) {
            throw new ConflictException("This time slot is already booked by another patient");
        }

        // Create and save the appointment
        Appointment appointment = new Appointment(patient, doctor, availability, appointmentRequest.getDate());
        appointment.setNotes(appointmentRequest.getNotes());
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Mark availability as booked
        availabilityService.markAvailabilityAsBooked(appointmentRequest.getAvailabilityId());

        return appointmentMapper.toDTO(savedAppointment);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        return appointmentMapper.toDTO(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient", "id", patientId);
        }
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(appointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor", "id", doctorId);
        }
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(appointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByPatientAndStatus(Long patientId, AppointmentStatus status) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient", "id", patientId);
        }
        return appointmentRepository.findByPatientIdAndStatus(patientId, status).stream()
                .map(appointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getAppointmentsByDoctorAndStatus(Long doctorId, AppointmentStatus status) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor", "id", doctorId);
        }
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, status).stream()
                .map(appointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getUpcomingAppointmentsByPatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient", "id", patientId);
        }
        return appointmentRepository.findByPatientIdAndDateAfter(patientId, LocalDate.now()).stream()
                .map(appointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> getUpcomingAppointmentsByDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor", "id", doctorId);
        }
        return appointmentRepository.findByDoctorIdAndDateAfter(doctorId, LocalDate.now()).stream()
                .map(appointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        if (appointment.getStatus() == AppointmentStatus.CANCELED) {
            throw new BadRequestException("Appointment is already canceled");
        }

        appointment.setStatus(AppointmentStatus.CANCELED);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Mark availability as available again
        availabilityService.markAvailabilityAsAvailable(appointment.getAvailability().getId());

        return appointmentMapper.toDTO(updatedAppointment);
    }

    @Override
    public AppointmentDTO updateAppointmentNotes(Long appointmentId, String notes) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        appointment.setNotes(notes);
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toDTO(updatedAppointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        // If appointment is booked, mark availability as available again
        if (appointment.getStatus() == AppointmentStatus.BOOKED) {
            availabilityService.markAvailabilityAsAvailable(appointment.getAvailability().getId());
        }

        appointmentRepository.delete(appointment);
    }
}