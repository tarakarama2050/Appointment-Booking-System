package com.appointment.booking.repository;

import com.appointment.booking.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecializationIgnoreCase(String specialization);
    List<Doctor> findByNameContainingIgnoreCase(String name);

    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Doctor> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT d.specialization FROM Doctor d ORDER BY d.specialization")
    List<String> findAllSpecializations();
}