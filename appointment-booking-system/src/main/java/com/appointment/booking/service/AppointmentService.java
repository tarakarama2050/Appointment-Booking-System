package com.appointment.booking.service;

import com.appointment.booking.dto.AppointmentDTO;
import com.appointment.booking.dto.AppointmentRequest;
import com.appointment.booking.entity.Appointment.AppointmentStatus;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    AppointmentDTO bookAppointment(AppointmentRequest appointmentRequest);
    AppointmentDTO getAppointmentById(Long id);
    List<AppointmentDTO> getAppointmentsByPatient(Long patientId);
    List<AppointmentDTO> getAppointmentsByDoctor(Long doctorId);
    List<AppointmentDTO> getAppointmentsByPatientAndStatus(Long patientId, AppointmentStatus status);
    List<AppointmentDTO> getAppointmentsByDoctorAndStatus(Long doctorId, AppointmentStatus status);
    List<AppointmentDTO> getUpcomingAppointmentsByPatient(Long patientId);
    List<AppointmentDTO> getUpcomingAppointmentsByDoctor(Long doctorId);
    AppointmentDTO cancelAppointment(Long appointmentId);
    AppointmentDTO updateAppointmentNotes(Long appointmentId, String notes);
    void deleteAppointment(Long appointmentId);
}