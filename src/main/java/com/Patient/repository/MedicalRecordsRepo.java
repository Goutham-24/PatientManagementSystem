package com.Patient.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Patient.model.MedicalRecords;

@Repository
public interface MedicalRecordsRepo extends JpaRepository<MedicalRecords, Long> {

    @Query("SELECT r FROM MedicalRecords r WHERE r.date <= :localdate AND r.patient.id = :PatientId AND r.patient.doctor.doctorEmailId = :user ORDER BY r.date DESC")
    List<MedicalRecords> GetMedicalRecords(@Param("user") String user,@Param("PatientId") long PatientId,@Param("localdate") LocalDate localdate);

}
