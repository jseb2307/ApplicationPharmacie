package com.jseb23.NewPharmacie.Controller;

import com.jseb23.NewPharmacie.DTO.PatientDTO;
import com.jseb23.NewPharmacie.Model.Patient;
import com.jseb23.NewPharmacie.Service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jseb23.NewPharmacie.DTO.PatientDTO.mapPatientToDTO;

@RestController
@RequestMapping("/patient")
@AllArgsConstructor
public class PatientController {


    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.findById(id);

        return patient.map(p -> ResponseEntity.ok(mapPatientToDTO(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<Patient> patients = patientService.findAll();

        if (!patients.isEmpty()) {
            List<PatientDTO> patientDTOs = patients.stream()
                    .map(PatientDTO::mapPatientToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(patientDTOs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody Patient patient) {
        System.out.println("Informations du patient reçues : " + patient.toString());
        Patient createdPatient = patientService.save(patient);
        PatientDTO patientDTO = mapPatientToDTO(createdPatient);
        System.out.println("Patient créé : " + patientDTO.toString());

        return ResponseEntity.ok(patientDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Optional<Patient> existingPatient = patientService.findById(id);

        if (existingPatient.isPresent()) {
            patient.setIdPatient(id);
            Patient updatedPatient = patientService.save(patient);
            PatientDTO updatedPatientDTO = mapPatientToDTO(updatedPatient);
            return ResponseEntity.ok(updatedPatientDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id) {
        Optional<Patient> existingPatient = patientService.findById(id);

        if (existingPatient.isPresent()) {
            patientService.deleteById(id);
            return ResponseEntity.ok("Patient with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}