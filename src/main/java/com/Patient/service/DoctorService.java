package com.Patient.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.Patient.Exception.DoctorNotFoundException;
import com.Patient.model.Doctor;
import com.Patient.repository.DoctorRepo;

@Service
public class DoctorService implements UserDetailsService{

    @Autowired
    DoctorRepo Doctorrepo;

     @Override
     public UserDetails loadUserByUsername(String username) {
        Doctor doctor = Doctorrepo.findByDoctorEmailId(username).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        return new User(
            doctor.getDoctorEmailId(),
            doctor.getPassword(),
            new ArrayList<>()
        );
    } 
    
}
