package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Patient;
import com.jseb23.NewPharmacie.Repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService
{

    private final PatientRepository patientRepository;
    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> findAllById(Iterable<Long> ids) {
        return patientRepository.findAllById(ids);
    }

    @Override
    public boolean existsById(Long id) {
        return patientRepository.existsById(id);
    }

    @Override
    public long count() {
        return patientRepository.count();
    }

    @Override
    public <S extends Patient> S save(S entity) {
        return patientRepository.save(entity);
    }

    @Override
    public <S extends Patient> List<S> saveAll(Iterable<S> entities) {
        return patientRepository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public void delete(Patient entity) {
        patientRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        patientRepository.deleteAll();
    }
}
