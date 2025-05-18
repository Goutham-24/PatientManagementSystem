package com.Patient.DTOclasses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PatientDto {
    @NotNull(message = "Name cannot be null")
    private String patientName;
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String patientEmail;
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits and can start with '+'")
    @NotNull(message = "Phone number cannot be null")
    private String patientPhonenumber;
    
    private Long doctorId; 


    public String getPatientName() {
        return patientName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public String getPatientPhonenumber() {
        return patientPhonenumber;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    
   
    public void setPatientName(String name) {
        this.patientName = name;
    }

    public void setPatientEmail(String email) {
        this.patientEmail = email;
    }

    public void setPatientPhonenumber(String phoneNumber) {
        this.patientPhonenumber = phoneNumber;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}

