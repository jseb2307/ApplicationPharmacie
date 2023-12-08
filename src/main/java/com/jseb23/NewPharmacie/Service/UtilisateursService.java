package com.jseb23.NewPharmacie.Service;


import com.jseb23.NewPharmacie.Utilisateurs.Utilisateurs;


import java.util.List;
import java.util.Optional;

public interface UtilisateursService {

    // Méthodes de lecture
    Optional<Utilisateurs> findById(Long id);
    List<Utilisateurs> findAll();
    List<Utilisateurs> findAllById(Iterable<Long> ids);
    boolean existsById(Long id);
    long count();

    // Méthodes de sauvegarde
    <S extends Utilisateurs> S save(S entity);
    <S extends Utilisateurs> List<S> saveAll(Iterable<S> entities);

    // Méthodes de recherche

    Optional<Utilisateurs> findByUtilisateur(String utilisateur);

    // Méthodes de suppression
    void deleteById(Long id);
    void delete(Utilisateurs entity);
    void deleteAll();

}