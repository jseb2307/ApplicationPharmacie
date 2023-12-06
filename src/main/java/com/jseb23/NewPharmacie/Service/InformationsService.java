package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Informations;


import java.util.List;
import java.util.Optional;

public interface InformationsService {

    // Méthodes de lecture
    Optional<Informations> findById(Long id);
    List<Informations> findAll();
    List<Informations> findAllById(Iterable<Long> ids);
    boolean existsById(Long id);
    long count();

    // Méthodes de sauvegarde
    <S extends Informations> S save(S entity);
    <S extends Informations> List<S> saveAll(Iterable<S> entities);

    // Méthodes de suppression
    void deleteById(Long id);
    void delete(Informations entity);
    void deleteAll();

}