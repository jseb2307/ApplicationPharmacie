package com.jseb23.NewPharmacie.Service;


import com.jseb23.NewPharmacie.Model.Mutuelle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MutuelleService
{
    // Méthodes de lecture
    Optional<Mutuelle> findById(Long id);
    List<Mutuelle> findAll();
    List<Mutuelle> findAllById(Iterable<Long> ids);
    boolean existsById(Long id);
    long count();

    // Méthodes de sauvegarde
    <S extends Mutuelle> S save(S entity);
    <S extends Mutuelle> List<S> saveAll(Iterable<S> entities);

    // Méthodes de suppression
    void deleteById(Long id);
    void delete(Mutuelle entity);
    void deleteAll();
}
