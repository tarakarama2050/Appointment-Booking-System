package com.appointment.booking.repository;

import com.appointment.booking.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Query("SELECT p FROM Patient p WHERE p.email = :email AND p.id != :id")
    Optional<Patient> findByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT p FROM Patient p WHERE p.phone = :phone AND p.id != :id")
    Optional<Patient> findByPhoneAndIdNot(@Param("phone") String phone, @Param("id") Long id);
}