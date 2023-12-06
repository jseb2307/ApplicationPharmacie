package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface PatientService {

// Méthodes de lecture
    Optional<Patient> findById(Long id);
    List<Patient> findAll();
    List<Patient> findAllById(Iterable<Long> ids);
    boolean existsById(Long id);
    long count();

    // Méthodes de sauvegarde
    <S extends Patient> S save(S entity);
    <S extends Patient> List<S> saveAll(Iterable<S> entities);

    // Méthodes de suppression
    void deleteById(Long id);
    void delete(Patient entity);
    void deleteAll();

}
