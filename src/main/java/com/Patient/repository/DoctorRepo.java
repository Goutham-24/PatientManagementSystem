package com.Patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Patient.model.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    
    Optional<Doctor> findByDoctorEmailId(String emailId);
}