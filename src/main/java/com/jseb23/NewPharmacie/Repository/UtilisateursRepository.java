package com.jseb23.NewPharmacie.Repository;

import com.jseb23.NewPharmacie.Utilisateurs.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateursRepository extends JpaRepository<Utilisateurs,Long> {

    Optional<Utilisateurs> findByUtilisateur(String utilisateur);
}