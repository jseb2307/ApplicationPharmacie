package com.jseb23.NewPharmacie.Repository;

import com.jseb23.NewPharmacie.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByNomContaining(String nom);

    List<Patient> findByDateNaissanceBetween(LocalDate dateDebut, LocalDate dateFin);

   // List<Patient> findBySexe(Sexe sexe);
}