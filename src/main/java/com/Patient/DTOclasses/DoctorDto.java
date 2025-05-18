package com.Patient.DTOclasses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DoctorDto {
    @NotNull(message = "Name cannot be null")
    private String doctorName;
    @NotNull(message = "specialization cannot be null")
    private String specialization;
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be between 10 to 15 digits and can start with '+'")
    @NotNull(message = "Phone number cannot be null")
    private String doctorPhonenumber;
    
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String name) {
        this.doctorName = name;
    }

    public String getSpecialization(){
        return specialization;
    }

    public String getDoctorPhonenumber(){
        return doctorPhonenumber;
    }

    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }

    public void setDoctorPhonenumber(String phoneNumber){
        this.doctorPhonenumber = phoneNumber;
    }

}
