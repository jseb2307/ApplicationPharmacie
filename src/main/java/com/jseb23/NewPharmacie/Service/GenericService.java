package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


@Service
public class GenericService<T, ID> {

    public final GenericRepository<T, ID> repository;

    @Autowired
    public GenericService(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    /**
     * Sauvegarde une entité.
     *
     * @param entity L'entité à sauvegarder.
     * @return L'entité sauvegardée.
     */
    public T save(T entity) {
        return repository.save(entity);
    }

    /**
     * Trouve une entité par son identifiant.
     *
     * @param id L'identifiant de l'entité à trouver.
     * @return L'entité trouvée, ou null si elle n'existe pas.
     */
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Trouve toutes les entités.
     *
     * @return La liste de toutes les entités.
     */
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * Met à jour une entité.
     *
     * @param entity L'entité à mettre à jour.
     * @return L'entité mise à jour.
     */
    public T update(T entity) {
        return repository.save(entity);
    }

    /**
     * Supprime une entité par son identifiant.
     *
     * @param id L'identifiant de l'entité à supprimer.
     */
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    /**
     * Trouve toutes les entités qui correspondent à une certaine condition.
     *
     * @param predicate La condition à appliquer.
     * @return La liste des entités qui correspondent à la condition.
     */
    public List<T> findAll(Predicate<T> predicate) {
        return repository.findAll((Sort) predicate);
    }

//    /**
//     * Compte le nombre d'entités qui correspondent à une certaine condition.
//     *
//     * @param predicate La condition à appliquer.
//     * @return Le nombre d'entités qui correspondent à la condition.
//     */
//    public long count(Predicate<T> predicate) {
//        return repository.count(predicate);
//    }
}

