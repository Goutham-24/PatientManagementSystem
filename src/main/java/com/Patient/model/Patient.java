package com.Patient.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Patient {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private String patientEmailId;
    private String patientPhonenumber;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private Doctor doctor;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MedicalRecords> medicalRecords;

    public Long getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientEmailId() {
        return patientEmailId;
    }

    public String getPatientPhonenumber() {
        return patientPhonenumber;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public List<MedicalRecords> getMedicalRecords(){
        return medicalRecords;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setPatientName(String name) {
        this.patientName = name;
    }

    public void setPatientEmailId(String email) {
        this.patientEmailId = email;
    }

    public void setPatientPhonenumber(String phoneNumber) {
        this.patientPhonenumber = phoneNumber;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setMedicalRecords(List<MedicalRecords> Medicalrec){
        this.medicalRecords = Medicalrec;
    }
}