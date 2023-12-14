package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Docteur;


import java.util.List;
import java.util.Optional;

public interface DocteurService
{
    // Méthodes de lecture
    Optional<Docteur> findById(Long id);
    List<Docteur> findAll();
    List<Docteur> findAllById(Iterable<Long> ids);
    boolean existsById(Long id);
    long count();

    // Méthodes de sauvegarde
    <S extends Docteur> S save(S entity);
    <S extends Docteur> List<S> saveAll(Iterable<S> entities);

    // Méthodes de suppression
    void deleteById(Long id);
    void delete(Docteur entity);
    void deleteAll();
}
