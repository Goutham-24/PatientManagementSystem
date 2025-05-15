package com.Patient.controller;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
import com.Patient.model.MedicalRecords;
import com.Patient.service.PatientService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/Doctor")
public class ControllerPatient {
    @Autowired
    PatientService patientServices;

    @PostMapping("/NewPatient")
    public ResponseEntity<String> addPatientToDoctor(@Valid @RequestBody PatientDto patients,Principal prince){
        String user = prince.getName();
        return patientServices.newPatientAdd(user,patients);
        
    }

    @PostMapping("/RegisterDoctor")
    public  ResponseEntity<String> addDoctorDetails(@RequestBody  Doctor doctor){
        return patientServices.DoctorInclusion(doctor);
    }

    @PostMapping("/patient/{PatientId}/NewMedicalRecord")
    public  ResponseEntity<String> addMedicalRecord(@Valid @RequestBody MedicalRecordsDto medrec,@PathVariable long PatientId,Principal prince){
        String user = prince.getName();
        return patientServices.newRecord(medrec,PatientId,user);
    }

    @GetMapping
    public String getUserName(Principal prince){
       String user = prince.getName();
       return user;
    }

    @GetMapping("/Patients")
    public ResponseEntity<List<PatientDto>> recieveAllPatient(Principal prince){
        String user = prince.getName();
        List<PatientDto> patients = patientServices.totalPatient(user);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/Patient/{PatientId}")
    public ResponseEntity<PatientDto> recieveAPatient(@PathVariable long PatientId,Principal prince){
        String user = prince.getName();
        PatientDto patient =patientServices.singlePatient(user,PatientId);
        return ResponseEntity.ok(patient);
    }

    
    @GetMapping("/Patient/{PatientId}/MedicalRecords")
    public ResponseEntity<List<MedicalRecords>> recieveAllMedicalRecords(@PathVariable long PatientId,Principal prince){
        String user = prince.getName();
        List<MedicalRecords> record = patientServices.totalMedrecord(user,PatientId);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/All")
    public ResponseEntity<List<DoctorDto>> allDoctor(){
        List<DoctorDto> doctors = patientServices.totalDoctor();
        return ResponseEntity.ok(doctors);
    }

    @PutMapping("/Update")
    public ResponseEntity<String> DoctorUpdate(Principal prince,@Valid @RequestBody DoctorDto doctorDto){
        String user = prince.getName();
        return patientServices.updateDoctor(user,doctorDto);
    }
     
    @PutMapping("/Patient/{PatientId}")
    public ResponseEntity<String> PatientUpdate(@PathVariable long PatientId,@Valid @RequestBody PatientDto patientDto,Principal prince){
        String user = prince.getName();
        return patientServices.updatePatient(user,PatientId,patientDto);
    }

    @DeleteMapping("/Patient/{PatientId}")
    public ResponseEntity<String> patientDelete(@PathVariable long PatientId,Principal prince){
        String user = prince.getName();
        return patientServices.DeletePatient(user,PatientId);
    }
   
    @DeleteMapping("/NewDoctor/{NewDoctorId}")
    public ResponseEntity<String> doctorDelete(Principal prince,@PathVariable long NewDoctorId){
        String user = prince.getName();
        return patientServices.DeleteDoctor(user, NewDoctorId);
    }

    

}