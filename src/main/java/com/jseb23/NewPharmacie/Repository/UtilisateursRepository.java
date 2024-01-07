package com.jseb23.NewPharmacie.Repository;

import com.jseb23.NewPharmacie.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateur,Long> {

    Optional<Utilisateur> findByUtilisateur(String utilisateur);
}