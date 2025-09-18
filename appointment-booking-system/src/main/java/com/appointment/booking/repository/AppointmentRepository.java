package com.appointment.booking.repository;

import com.appointment.booking.entity.Appointment;
import com.appointment.booking.entity.Appointment.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatus status);
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);

    Optional<Appointment> findByAvailabilityIdAndDate(Long availabilityId, LocalDate date);

    @Query("SELECT a FROM Appointment a WHERE a.availability.id = :availabilityId " +
           "AND a.date = :date AND a.status = 'BOOKED'")
    Optional<Appointment> findActiveAppointmentByAvailabilityAndDate(@Param("availabilityId") Long availabilityId,
                                                                    @Param("date") LocalDate date);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :patientId " +
           "AND a.date >= :fromDate ORDER BY a.date ASC")
    List<Appointment> findByPatientIdAndDateAfter(@Param("patientId") Long patientId,
                                                 @Param("fromDate") LocalDate fromDate);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
           "AND a.date >= :fromDate ORDER BY a.date ASC")
    List<Appointment> findByDoctorIdAndDateAfter(@Param("doctorId") Long doctorId,
                                                @Param("fromDate") LocalDate fromDate);

    boolean existsByAvailabilityIdAndDateAndStatus(Long availabilityId, LocalDate date, AppointmentStatus status);
}