package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Docteur;
import com.jseb23.NewPharmacie.Repository.DocteurRepository;
import com.jseb23.NewPharmacie.Repository.InformationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DocteurServiceImpl implements DocteurService
{

    private final DocteurRepository docteurRepository;
    @Override
    public Optional<Docteur> findById(Long id) {
        return docteurRepository.findById(id);
    }

    @Override
    public List<Docteur> findAll() {
        return docteurRepository.findAll();
    }

    @Override
    public List<Docteur> findAllById(Iterable<Long> ids) {
        return docteurRepository.findAllById(ids);
    }

    @Override
    public boolean existsById(Long id) {
        return docteurRepository.existsById(id);
    }

    @Override
    public long count() {
        return docteurRepository.count();
    }

    @Override
    public <S extends Docteur> S save(S entity) {
        return docteurRepository.save(entity);
    }

    @Override
    public <S extends Docteur> List<S> saveAll(Iterable<S> entities) {
        return docteurRepository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {

        docteurRepository.deleteById(id);

    }

    @Override
    public void delete(Docteur entity) {

        docteurRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        docteurRepository.deleteAll();

    }
}
