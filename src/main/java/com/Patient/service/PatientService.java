package com.Patient.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.Exception.DoctorNotFoundException;
import com.Patient.Exception.MedicalRecordNotFoundException;
import com.Patient.Exception.PatientNotFoundException;
import com.Patient.Exception.UserInvalidException;
import com.Patient.model.Doctor;
import com.Patient.model.MedicalRecords;
import com.Patient.model.Patient;
import com.Patient.repository.DoctorRepo;
import com.Patient.repository.MedicalRecordsRepo;
import com.Patient.repository.PatientRepo;

@Service
public class PatientService {
    
    @Autowired
    PatientRepo Patientrepo;

    @Autowired
    DoctorRepo Doctorrepo;

    @Autowired
    MedicalRecordsRepo MedicalRecordsrepo;

    @Autowired
    PasswordEncoder passwordEncoder;
    
   

    public ResponseEntity<String> newPatientAdd(String user,PatientDto patient){
        Patient newPatient = new Patient();
        newPatient.setPatientName(patient.getPatientName());
        newPatient.setPatientEmailId(patient.getPatientEmail());
        newPatient.setPatientPhonenumber(patient.getPatientPhonenumber());

        Doctor doctor= Doctorrepo.findByDoctorEmailId(user).orElseThrow(() -> new DoctorNotFoundException("Doctor not found!"));
        newPatient.setDoctor(doctor);
        Patientrepo.save(newPatient);
        
        return ResponseEntity.ok("The Patient has been added to the database successfully!");
        
    }

    public ResponseEntity<String> DoctorInclusion(Doctor doctor){

        Optional<Doctor> d = Doctorrepo.findByDoctorEmailId(doctor.getDoctorEmailId());

        if(!d.isEmpty()){
            throw new IllegalArgumentException("email id already exists!");
        }


        if (doctor.getPassword() == null || doctor.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
    
        Doctorrepo.save(doctor);

        return ResponseEntity.ok("The Doctor has been included to the database successfully");
    }

    public ResponseEntity<String> newRecord( MedicalRecordsDto medrec,long PatientId,String user){
        Patient patient = Patientrepo.GetPatientDetails(user, PatientId).orElseThrow(()-> new UserInvalidException("invalid user or patientId"));


        MedicalRecords med = new MedicalRecords();
        med.setSymptoms(medrec.getSymptoms());
        med.setDiagnosis(medrec.getDiagnosis());
        med.setPrescription(medrec.getPrescription());
        med.setDate(LocalDate.now());
        
        //Patient p = Patientrepo.findById(medrec.getPatientId()).orElseThrow(()-> new PatientNotFoundException("Patient not found!"));
        med.setPatient(patient);
        MedicalRecordsrepo.save(med);

        return ResponseEntity.ok("The Medical Record for Patient "+PatientId+" has been added to the database successfully");
    }

    @Transactional
    public List<PatientDto> totalPatient(String user){
        Doctor doc = Doctorrepo.findByDoctorEmailId(user).orElseThrow(()->new DoctorNotFoundException("Doctor Not found!"));
        List<Patient> l = doc.getList();
        List<PatientDto> pdto = new ArrayList<>();
        for(Patient i : l){
            PatientDto p = new PatientDto();

            p.setPatientName(i.getPatientName());
            p.setPatientEmail(i.getPatientEmailId());
            p.setPatientPhonenumber(i.getPatientPhonenumber());
            
            pdto.add(p);
        }

        return pdto;
    }

    public PatientDto singlePatient(String user,long PatientId){
        if(!Patientrepo.existsById(PatientId)){
            throw new PatientNotFoundException("Patient not found in the Database");
        }
        
        Patient p =  Patientrepo.GetPatientDetails(user,PatientId).orElseThrow(()->new UserInvalidException("You are not allowed to access the patient!"));

        PatientDto pdto = new PatientDto();
        pdto.setPatientName(p.getPatientName());
        pdto.setPatientEmail(p.getPatientEmailId());
        pdto.setPatientPhonenumber(p.getPatientPhonenumber());

        return pdto;
    }

    public List<MedicalRecords> totalMedrecord(String user, long patientId){
        if(!Patientrepo.existsById(patientId)){
            throw new PatientNotFoundException("Patient is not available in the database!");
        }
        Patientrepo.GetPatientDetails(user, patientId).orElseThrow(()-> new UserInvalidException("You are not allowed to access the patient!"));

        List<MedicalRecords> med = MedicalRecordsrepo.GetMedicalRecords(user,patientId,LocalDate.now());
        if(med.isEmpty()){
            throw new MedicalRecordNotFoundException("No Medical records exists for the paitent!");
        }

        return med;
    }

    public List<DoctorDto> totalDoctor(){
        List<Doctor> docs =  Doctorrepo.findAll();

        List<DoctorDto> dto = new ArrayList<>();

        for(Doctor i : docs){
            DoctorDto d = new DoctorDto();
            d.setDoctorName(i.getDoctorName());
            d.setDoctorPhonenumber(i.getDoctorPhonenumber());
            d.setSpecialization(i.getSpecialization());

            dto.add(d);
        }

        return dto;
    }

    public ResponseEntity<String> updateDoctor(String user,DoctorDto doctorDto){
        Doctor doc = Doctorrepo.findByDoctorEmailId(user).orElseThrow(()-> new DoctorNotFoundException("Could not find ID "+user));

        doc.setDoctorName(doctorDto.getDoctorName());
        doc.setSpecialization(doctorDto.getSpecialization());
        doc.setDoctorPhonenumber(doctorDto.getDoctorPhonenumber());

        Doctorrepo.save(doc);
        return ResponseEntity.ok("The Doctor details have been Successfully updated!");
    }

    public ResponseEntity<String> updatePatient(String user,long PatientId,PatientDto patientDto){
        if(!Patientrepo.existsById(PatientId)){
            throw new PatientNotFoundException("Patient not found!");
        }
        Patient patient = Patientrepo.GetPatientDetails(user,PatientId).orElseThrow(()->new UserInvalidException("You are not allowed to access the patient!"));

        patient.setPatientEmailId(patientDto.getPatientEmail());
        patient.setPatientPhonenumber(patientDto.getPatientPhonenumber());

        Patientrepo.save(patient);

        return ResponseEntity.ok("The Patient of ID "+PatientId+" has been successfully updated!");

    }

    public ResponseEntity<String> DeletePatient(String user,long PatientId){
        if(!Patientrepo.existsById(PatientId)){
            throw new PatientNotFoundException("Patient not found!");
        }

        Patient patient = Patientrepo.GetPatientDetails(user,PatientId).orElseThrow(()->new UserInvalidException("You are not allowed to access the patient!"));
        List<MedicalRecords> medrec = patient.getMedicalRecords();

        for(MedicalRecords i : medrec){
            MedicalRecordsrepo.deleteById(i.getId());
        }

        Patientrepo.deleteById(PatientId);

        return ResponseEntity.ok("The Patient of ID "+PatientId+" has been deleted along with all their records!");
    }

    public ResponseEntity<String> DeleteDoctor(String user, long NewDoctorId){
        Doctor doc = Doctorrepo.findByDoctorEmailId(user).orElseThrow(()-> new DoctorNotFoundException("Could not find ID "+user));
        List<Patient> p = doc.getList();
        Doctor D = Doctorrepo.findById(NewDoctorId).orElseThrow(()-> new DoctorNotFoundException("Could not find Replacement Doctor ID "+NewDoctorId));
        for(Patient i : p){
            i.setDoctor(D);
            Patientrepo.save(i);
        }

        Doctorrepo.delete(doc);

        return ResponseEntity.ok("The Doctor of ID "+user+" has been deleted Successfully");
    }
}
