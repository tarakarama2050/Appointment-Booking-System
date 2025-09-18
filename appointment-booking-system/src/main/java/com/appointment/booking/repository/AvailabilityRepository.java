package com.appointment.booking.repository;

import com.appointment.booking.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByDoctorId(Long doctorId);
    List<Availability> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Availability> findByDoctorIdAndDateAndIsAvailable(Long doctorId, LocalDate date, Boolean isAvailable);
    List<Availability> findByDateAndIsAvailable(LocalDate date, Boolean isAvailable);

    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND a.date = :date " +
           "AND ((a.startTime <= :startTime AND a.endTime > :startTime) " +
           "OR (a.startTime < :endTime AND a.endTime >= :endTime) " +
           "OR (a.startTime >= :startTime AND a.endTime <= :endTime))")
    List<Availability> findOverlappingAvailabilities(@Param("doctorId") Long doctorId,
                                                    @Param("date") LocalDate date,
                                                    @Param("startTime") LocalTime startTime,
                                                    @Param("endTime") LocalTime endTime);

    @Query("SELECT a FROM Availability a WHERE a.doctor.id = :doctorId AND a.date >= :fromDate ORDER BY a.date, a.startTime")
    List<Availability> findByDoctorIdAndDateAfter(@Param("doctorId") Long doctorId, @Param("fromDate") LocalDate fromDate);

    Optional<Availability> findByIdAndIsAvailable(Long id, Boolean isAvailable);
}