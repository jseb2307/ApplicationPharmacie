package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Repository.InformationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InformationsServiceImpl implements InformationsService
{

    private final InformationsRepository informationsRepository;
    @Override
    public Optional<Informations> findById(Long id) {
        return informationsRepository.findById(id);
    }

    @Override
    public List<Informations> findAll() {
        return informationsRepository.findAll();
    }

    @Override
    public List<Informations> findAllById(Iterable<Long> ids) {
        return informationsRepository.findAllById(ids);
    }

    @Override
    public boolean existsById(Long id) {
        return informationsRepository.existsById(id);
    }

    @Override
    public long count() {
        return informationsRepository.count();
    }

    @Override
    public <S extends Informations> S save(S entity) {
        return informationsRepository.save(entity);
    }

    @Override
    public <S extends Informations> List<S> saveAll(Iterable<S> entities) {
        return informationsRepository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        informationsRepository.deleteById(id);
    }

    @Override
    public void delete(Informations entity) {
        informationsRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        informationsRepository.deleteAll();
    }
}

