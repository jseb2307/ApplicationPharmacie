package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Mutuelle;

import java.util.List;
import java.util.Optional;

public class MutuelleServiceImpl implements MutuelleService
{

    private static MutuelleService mutuelleService;
    @Override
    public Optional<Mutuelle> findById(Long id) {
        return mutuelleService.findById(id);
    }

    @Override
    public List<Mutuelle> findAll() {
        return mutuelleService.findAll();
    }

    @Override
    public List<Mutuelle> findAllById(Iterable<Long> ids) {
        return mutuelleService.findAllById(ids);
    }

    @Override
    public boolean existsById(Long id) {
        return mutuelleService.existsById(id);
    }

    @Override
    public long count() {
        return mutuelleService.count();
    }

    @Override
    public <S extends Mutuelle> S save(S entity) {
        return mutuelleService.save(entity);
    }

    @Override
    public <S extends Mutuelle> List<S> saveAll(Iterable<S> entities) {
        return mutuelleService.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {

        mutuelleService.deleteById(id);
    }

    @Override
    public void delete(Mutuelle entity) {

        mutuelleService.delete(entity);
    }

    @Override
    public void deleteAll() {

        mutuelleService.deleteAll();
    }
}
