package com.Patient.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
import com.Patient.service.PatientService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/Thymeleaf")
public class FrontendController {

@Autowired
PatientService patientServices;
    
@GetMapping("/doctor-form")
public String registrationForm(Model model) {
    model.addAttribute("doctor", new Doctor());
    return "add-doctor";
}

@PostMapping("/addDoctor")
public String DoctorAdd(@ModelAttribute Doctor doctor, Model model){
    patientServices.DoctorInclusion(doctor);
    model.addAttribute("message", "Successfully added doctor!");
    return "success-page";
}

@GetMapping("/patient-form")
public String patientForm(Model model) {
    model.addAttribute("patient", new PatientDto());
    return "add-patient";
}

@PostMapping("/addPatient")
public String PatientAdd(@ModelAttribute PatientDto patients, Principal principal, Model model){
    String user = principal.getName();
    patientServices.newPatientAdd(user,patients);
    model.addAttribute("message", "Successfully added patient!");
    return "success-page";
}

@GetMapping("/medicalrecord-form/{patientId}")
public String medicalrecordForm(@PathVariable long patientId, Model model) {
    model.addAttribute("patientId", patientId);
    model.addAttribute("medicalrecord", new MedicalRecordsDto());
    return "add-medicalrecord";
}

@PostMapping("/addMedicalrecord/{patientId}")
public String MedicalRecordAdd(@ModelAttribute MedicalRecordsDto medrec, Principal principal,@PathVariable long patientId , Model model){
    String user = principal.getName();
    patientServices.newRecord(medrec,patientId,user);
    model.addAttribute("message", "Successfully added medical record!");
    return "success-page";
}

@GetMapping("/DoctorUpdate")
public String doctorUpdateForm(Model model) {
    model.addAttribute("doctor", new DoctorDto());
    return "doctor-update";
}

@PutMapping("/DoctorUpdate-form")
public String UpdateDoctorForm(@ModelAttribute DoctorDto doctorDto, Principal prince, Model model){
    String user = prince.getName();
    patientServices.updateDoctor(user,doctorDto);
    model.addAttribute("message", "Successfully Updated Doctor!");
    return "success-page";
}

@GetMapping("/PatientUpdate/{patientId}")
public String patientUpdateForm(@PathVariable long patientId, Model model) {
    model.addAttribute("patientId", patientId);
    model.addAttribute("patient", new PatientDto());
    return "patient-update";
}

@PutMapping("/PatientUpdate-form/{patientId}")
public String UpdatePatientForm(@ModelAttribute PatientDto patientDto, Principal principal,@PathVariable long patientId , Model model){
    String user = principal.getName();
    patientServices.updatePatient(user,patientId,patientDto);
    model.addAttribute("message", "Successfully Updated Patient!");
    return "success-page";
}

}
