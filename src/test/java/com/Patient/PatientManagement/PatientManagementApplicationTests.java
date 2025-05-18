package com.Patient.PatientManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Patient.DTOclasses.DoctorDto;
import com.Patient.DTOclasses.MedicalRecordsDto;
import com.Patient.DTOclasses.PatientDto;
import com.Patient.model.Doctor;
import com.Patient.model.MedicalRecords;
import com.Patient.model.Patient;
import com.Patient.repository.DoctorRepo;
import com.Patient.repository.MedicalRecordsRepo;
import com.Patient.repository.PatientRepo;
import com.Patient.service.PatientService;

@ExtendWith(MockitoExtension.class)
class PatientManagementApplicationTests {
	@Mock
    PatientRepo Patientrepo;

    @Mock
    DoctorRepo Doctorrepo;

    @Mock
    MedicalRecordsRepo MedicalRecordsrepo;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	PatientService patientServices;

	@Test
	void testNewPatientAdd_returnStatus_whenUserEmailValid() {
		String username = "Someone@gmail.com";

		PatientDto pdto = new PatientDto();
		pdto.setPatientName("relox");
		pdto.setPatientEmail("rolex@gmail.com");
		pdto.setPatientPhonenumber("9177736351");

		Doctor doctor = new Doctor();
		doctor.setDoctorEmailId(username);

		when(Doctorrepo.findByDoctorEmailId(username)).thenReturn(Optional.of(doctor));

		ResponseEntity<String> res= patientServices.newPatientAdd(username,pdto);

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Patient has been added to the database successfully!", res.getBody());

		verify(Doctorrepo,times(1)).findByDoctorEmailId(username);
	}

	@Test
	void testDoctorAdd_returnStatus_whenPasswordProvided(){
		Doctor doctor = new Doctor();
		doctor.setDoctorName("Kurtz");
		doctor.setDoctorEmailId("one@gmail.com");
		doctor.setSpecialization("eye specialist");
		doctor.setDoctorPhonenumber("9198827771");
		doctor.setPassword("2222");

		String originalPass = "2222";
		String pass = "$2a$10$ZOHcG.QFfVi5RKRx4DUU2OvUL5kZRTDkHQktjnpMOwbl.ITZ4up/m";

		when(passwordEncoder.encode(doctor.getPassword())).thenReturn(pass);

		ResponseEntity<String> res = patientServices.DoctorInclusion(doctor);

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Doctor has been included to the database successfully", res.getBody());
		assertEquals(pass, doctor.getPassword());

		verify(passwordEncoder,times(1)).encode(originalPass);
	}
	
	@Test
	void testMedicalRecordAdd_returnStatus_whenUserAndPatientValid(){
		String username = "Someone@gmail.com";
		long patientId = 1;
		MedicalRecordsDto mdto = new MedicalRecordsDto();
		mdto.setSymptoms("eye pain");
		mdto.setDiagnosis("eye pressure");
		mdto.setPrescription("saline water rinse");

		Doctor doctor = new Doctor();
		doctor.setDoctorEmailId(username);
		
		Patient patient = new Patient();
		patient.setId((long) 1);
		patient.setDoctor(doctor);

		when(Patientrepo.GetPatientDetails(username, patientId)).thenReturn(Optional.of(patient));


		ResponseEntity<String> res = patientServices.newRecord( mdto,patientId,username);

		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Medical Record for Patient "+patientId+" has been added to the database successfully", res.getBody());

		verify(Patientrepo,times(1)).GetPatientDetails(username, patientId);

	}

	@Test
	void testTotalPatient_returnsPatientDto_whenUserEmailValid(){
		String username = "Someone@gmail.com";

		Doctor doctor = new Doctor();
		doctor.setDoctorEmailId(username);

		List<Patient> patient = new ArrayList<>();

		Patient p1 = new Patient();
		p1.setPatientName("name1");
		p1.setPatientEmailId("name1@gmail.com");
		p1.setPatientPhonenumber("111111111");

		Patient p2 = new Patient();
		p2.setPatientName("name2");
		p2.setPatientEmailId("name2@gmail.com");
		p2.setPatientPhonenumber("222222222");
		
		patient.add(p1);
		patient.add(p2);
		doctor.setList(patient);

		when(Doctorrepo.findByDoctorEmailId(username)).thenReturn(Optional.of(doctor));

		List<PatientDto> res = patientServices.totalPatient(username);

		assertEquals(2, res.size());
		assertEquals("name1", res.get(0).getPatientName());
		assertEquals("name2@gmail.com", res.get(1).getPatientEmail());

		verify(Doctorrepo,times(1)).findByDoctorEmailId(username);


	}

	@Test
	void testSinglePatient_returnsPatientDto_whenUserAndpatientValid(){
		String username = "Someone@gmail.com";
		long patientId = 1;

		Doctor doctor = new Doctor();
		doctor.setDoctorEmailId(username);

		Patient patient = new Patient();
		patient.setId((long)1);
		patient.setPatientName("Name1");
		patient.setDoctor(doctor);
		patient.setPatientEmailId("name1@gmail.com");
		patient.setPatientPhonenumber("9191929298");
		

		when(Patientrepo.existsById(patientId)).thenReturn(true);
		when(Patientrepo.GetPatientDetails(username,patientId)).thenReturn(Optional.of(patient));

		PatientDto res = patientServices.singlePatient(username,patientId);

		assertEquals("Name1", res.getPatientName());
		assertEquals("name1@gmail.com", res.getPatientEmail());

		verify(Patientrepo,times(1)).existsById(patientId);
		verify(Patientrepo,times(1)).GetPatientDetails(username,patientId);
	}

	@Test
	void testTotalMedicalRecords_returnsMedicalRecordsList(){
		String username = "Someone@gmail.com";
		long patientId = 1;

		Doctor doctor = new Doctor();
		doctor.setDoctorEmailId(username);

		Patient patient = new Patient();
		patient.setId((long)1);
		patient.setPatientName("Name1");
		patient.setDoctor(doctor);
		patient.setPatientEmailId("name1@gmail.com");
		patient.setPatientPhonenumber("9191929298");

		List<MedicalRecords> medrec = new ArrayList<>();

		MedicalRecords med1 = new MedicalRecords();

		med1.setSymptoms("symptom1");
		med1.setDiagnosis("diagnosis1");
		med1.setPrescription("prescription1");
		med1.setDate(LocalDate.now());
		med1.setPatient(patient);

		MedicalRecords med2 = new MedicalRecords();

		med2.setSymptoms("symptom2");
		med2.setDiagnosis("diagnosis2");
		med2.setPrescription("prescription2");
		med2.setDate(LocalDate.now());;
		med2.setPatient(patient);

		medrec.add(med1);
		medrec.add(med2);
		when(Patientrepo.existsById(patientId)).thenReturn(true);
		when(Patientrepo.GetPatientDetails(username,patientId)).thenReturn(Optional.of(patient));
		when(MedicalRecordsrepo.GetMedicalRecords(username,patientId,LocalDate.now())).thenReturn(medrec);
		

		List<MedicalRecords> res = patientServices.totalMedrecord(username,patientId);

		assertEquals(2, res.size());
		assertEquals("symptom1", res.get(0).getSymptoms());
		assertEquals("prescription2", res.get(1).getPrescription());

		verify(Patientrepo,times(1)).GetPatientDetails(username,patientId);
		verify(MedicalRecordsrepo,times(1)).GetMedicalRecords(username,patientId,LocalDate.now());
	}
	@Test
	void testAllDoctor_returnsDoctorDtoList(){
		List<Doctor> doctors = new ArrayList<>();
		Doctor d1 = new Doctor();
        d1.setDoctorName("Dr. Shawn");
        d1.setDoctorPhonenumber("1234567890");
        d1.setSpecialization("Surgery");
		Doctor d2= new Doctor();
		d2.setDoctorName("Dr. Aaron");
		d2.setDoctorPhonenumber("0987654321");
		d2.setSpecialization("Neurology");
		doctors.add(d1);
		doctors.add(d2);

		when(Doctorrepo.findAll()).thenReturn(doctors);
		List<DoctorDto> res = patientServices.totalDoctor();
		assertEquals(2, res.size());
		assertEquals("Dr. Shawn", res.get(0).getDoctorName());
		assertEquals("0987654321", res.get(1).getDoctorPhonenumber());
		assertEquals("Surgery", res.get(0).getSpecialization());
		assertEquals("Neurology", res.get(1).getSpecialization());
		verify(Doctorrepo,times(1)).findAll();

	}
	@Test
	void testupdateDoctor_returnStatus_whenDoctorExists(){
		String username = "shawn@gmail.com";
		Doctor d = new Doctor();
		d.setDoctorEmailId(username);
		d.setDoctorName("Dr. Shawn");
		d.setDoctorPhonenumber("9191919191");
		d.setSpecialization("Surgery");

		DoctorDto updateDoctorDto = new DoctorDto();
		updateDoctorDto.setDoctorName("Dr. Shawn Murphy");
		updateDoctorDto.setDoctorPhonenumber("9191919192");
		updateDoctorDto.setSpecialization("Cardiology");

		when(Doctorrepo.findByDoctorEmailId(username)).thenReturn(Optional.of(d));
		ResponseEntity<String> res = patientServices.updateDoctor(username,updateDoctorDto);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Doctor details have been Successfully updated!", res.getBody());
		assertEquals("Dr. Shawn Murphy", d.getDoctorName());
		assertEquals("9191919192", d.getDoctorPhonenumber());
		verify(Doctorrepo,times(1)).findByDoctorEmailId(username);

	}

	@Test
	void testupdatePatient_returnStatus_whenuserAndPatientValid(){
		String username = "shawn@gmail.com";
		long patientId = 1;
		Patient p = new Patient();
		p.setPatientEmailId("rolex1@gmail.com");
		p.setPatientPhonenumber("9191919191");

		PatientDto updatePatientDto = new PatientDto();
		updatePatientDto.setPatientEmail("rolex2@gmail.com");
		updatePatientDto.setPatientPhonenumber("9191919192");

		when(Patientrepo.existsById(patientId)).thenReturn(true);
		when(Patientrepo.GetPatientDetails(username,patientId)).thenReturn(Optional.of(p));
		ResponseEntity<String> res = patientServices.updatePatient(username,patientId,updatePatientDto);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Patient of ID " +patientId+ " has been successfully updated!", res.getBody());
		assertEquals("rolex2@gmail.com", p.getPatientEmailId());
		assertEquals("9191919192", p.getPatientPhonenumber());
		verify(Patientrepo,times(1)).existsById(patientId);
	}

	@Test
	void testDeletePatient_returnStatus_whenUserAndPatientValid(){
		String username = "shawn@gmail.com";
		long patientId = 1;
		Patient p = new Patient();
		p.setId(patientId);
		p.setPatientEmailId("rolex@gmail.com");
		p.setPatientPhonenumber("9191919191");

		List<MedicalRecords> medrec = new ArrayList<>();
		MedicalRecords m1 = new MedicalRecords();
		m1.setId(1L);
		m1.setSymptoms("symptom1");
		m1.setDiagnosis("diagnosis1");
		m1.setPrescription("prescription1");
		MedicalRecords m2 = new MedicalRecords();
		m2.setId(2L);
		m2.setSymptoms("symptom2");
		m2.setDiagnosis("diagnosis2");
		m2.setPrescription("prescription2");

		medrec.add(m1);
		medrec.add(m2);
		p.setMedicalRecords(medrec);

		when(Patientrepo.existsById(patientId)).thenReturn(true);
		when(Patientrepo.GetPatientDetails(username,patientId)).thenReturn(Optional.of(p));
		ResponseEntity<String> res = patientServices.DeletePatient(username,patientId);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Patient of ID " +patientId+ " has been deleted along with all their records!", res.getBody());
		verify(Patientrepo,times(1)).existsById(patientId);
		verify(Patientrepo,times(1)).GetPatientDetails(username,patientId);
		verify(MedicalRecordsrepo,times(1)).deleteById(1L);
		verify(MedicalRecordsrepo,times(1)).deleteById(2L);
		verify(Patientrepo,times(1)).deleteById(patientId);
	}

	@Test
	void testDeleteDoctor_returnStatus_whenDoctorValid(){
		String username = "shawn@gmail.com";
		long doctorId = 2;
		Doctor d = new Doctor();
		d.setDoctorEmailId(username);
		List<Patient> p = new ArrayList<>();
		Patient p1 = new Patient();
		Patient p2 = new Patient();
		p.add(p1);
		p.add(p2);
		d.setList(p);
		Doctor d1 = new Doctor();
		d1.setId(doctorId);
		d1.setDoctorEmailId("aaron@gmail.com");
		when(Doctorrepo.findByDoctorEmailId(username)).thenReturn(Optional.of(d));
		when(Doctorrepo.findById(doctorId)).thenReturn(Optional.of(d1));
		ResponseEntity<String> res = patientServices.DeleteDoctor(username,doctorId);
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("The Doctor of ID "+username+" has been deleted Successfully", res.getBody());


	}

}