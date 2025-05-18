package com.Patient.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String doctorName;
    private String doctorEmailId;
    private String specialization;
    private String doctorPhonenumber;
    private String Password;

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    private List<Patient> patients;

    public Long getId(){
        return id;
    }
    public String getDoctorName(){
        return doctorName;
    }

    public String getSpecializaion(){
        return specialization;
    }

    public String getDoctorPhonenumber(){
        return doctorPhonenumber;
    }


    public List<Patient> getList(){
        return patients;
    }

    public String getPassword(){
        return Password;
    }

    public String getDoctorEmailId(){
        return doctorEmailId;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setDoctorName(String name){
        this.doctorName = name;
    }
    public void setList(List<Patient> patients){
        this.patients = patients;
    }

    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }

    public void setDoctorPhonenumber(String phoneNumber){
        this.doctorPhonenumber = phoneNumber;
    }

    public void setPassword(String Password){
        this.Password = Password;
    }

    public void setDoctorEmailId(String emailId){
        this.doctorEmailId = emailId;
    }
}