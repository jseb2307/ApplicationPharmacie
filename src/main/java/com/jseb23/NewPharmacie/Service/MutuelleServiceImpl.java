package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Mutuelle;
import com.jseb23.NewPharmacie.Repository.MutuelleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class MutuelleServiceImpl implements MutuelleService
{
    private MutuelleRepository mutuelleRepository;

    @Override
    public Optional<Mutuelle> findById(Long id) {
        return mutuelleRepository.findById(id);
    }

    @Override
    public List<Mutuelle> findAll() {
        return mutuelleRepository.findAll();
    }

    @Override
    public List<Mutuelle> findAllById(Iterable<Long> ids) {
        return mutuelleRepository.findAllById(ids);
    }

    @Override
    public boolean existsById(Long id) {
        return mutuelleRepository.existsById(id);
    }

    @Override
    public long count() {
        return mutuelleRepository.count();
    }

    @Override
    public <S extends Mutuelle> S save(S entity) {
        return mutuelleRepository.save(entity);
    }

    @Override
    public <S extends Mutuelle> List<S> saveAll(Iterable<S> entities) {
        return mutuelleRepository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {

        mutuelleRepository.deleteById(id);
    }

    @Override
    public void delete(Mutuelle entity) {

        mutuelleRepository.delete(entity);
    }

    @Override
    public void deleteAll() {

        mutuelleRepository.deleteAll();
    }
}
