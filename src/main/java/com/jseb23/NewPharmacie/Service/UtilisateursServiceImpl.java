package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Repository.InformationsRepository;
import com.jseb23.NewPharmacie.Repository.UtilisateursRepository;
import com.jseb23.NewPharmacie.Utilisateurs.Utilisateurs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilisateursServiceImpl implements UtilisateursService
{

    private final UtilisateursRepository utilisateursRepository;
    @Override
    public Optional<Utilisateurs> findById(Long id) {
        return utilisateursRepository.findById(id);
    }

    @Override
    public List<Utilisateurs> findAll() {
        return utilisateursRepository.findAll();
    }

    @Override
    public List<Utilisateurs> findAllById(Iterable<Long> ids) {
        return utilisateursRepository.findAllById(ids);
    }

    @Override
    public boolean existsById(Long id) {
        return utilisateursRepository.existsById(id);
    }

    @Override
    public long count() {
        return utilisateursRepository.count();
    }

    @Override
    public <S extends Utilisateurs> S save(S entity) {
        return utilisateursRepository.save(entity);
    }

    @Override
    public <S extends Utilisateurs> List<S> saveAll(Iterable<S> entities) {
        return utilisateursRepository.saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        utilisateursRepository.deleteById(id);
    }

    @Override
    public void delete(Utilisateurs entity) {
        utilisateursRepository.delete(entity);
    }

    @Override
    public void deleteAll() {
        utilisateursRepository.deleteAll();
    }

    @Override
    public Optional<Utilisateurs> findByUtilisateur(String utilisateur) {
        return utilisateursRepository.findByUtilisateur(utilisateur);
    }
}


