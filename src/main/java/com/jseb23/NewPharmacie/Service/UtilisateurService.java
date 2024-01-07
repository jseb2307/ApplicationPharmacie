package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Role;
import com.jseb23.NewPharmacie.Model.Utilisateur;
import com.jseb23.NewPharmacie.Repository.RoleRepository;
import com.jseb23.NewPharmacie.Repository.UtilisateursRepository;
import com.jseb23.NewPharmacie.TypeDeRole;
import com.jseb23.NewPharmacie.Model.Validation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class UtilisateurService implements UserDetailsService
{

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private EntityManager entityManager;
    private UtilisateursRepository utilisateurRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;

    @Autowired
    public UtilisateurService(UtilisateursRepository utilisateurRepository, BCryptPasswordEncoder passwordEncoder, ValidationService validationService) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }


    public void inscription(Utilisateur utilisateur) {
        log.info("dans utilisateurService inscription");
        log.info("nom utilisateur "+ utilisateur.getNomUtilisateur());

        if(!utilisateur.getEmail().contains("@")) {
            throw  new RuntimeException("Votre mail invalide");
        }
        if(!utilisateur.getEmail().contains(".")) {
            throw  new RuntimeException("Votre mail invalide");
        }

        Optional<Utilisateur> utilisateurOptional = this.utilisateurRepository.findByUtilisateur(utilisateur.getUtilisateur()); // recherche si utilisateur existant
        if(utilisateurOptional.isPresent()) {
            throw  new RuntimeException("Personne déjà inscrite");
        }

        // vérification si mot de passe vide ou null
        String mdp = utilisateur.getMotDePasseUtilisateur();
        log.info("mot de passe = " +mdp);
        if(mdp == null || mdp.trim().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut être vide ou nul");
        }
        String mdpCrypte = this.passwordEncoder.encode(utilisateur.getMotDePasseUtilisateur()); // encodage mot de passe
        utilisateur.setMotDePasseUtilisateur(mdpCrypte);

        log.info("mdp codé " +mdpCrypte);

        // ================== Forcer les nouveaux inscrits à être "UTILISATEUR"  =================
        Role roleUtilisateur = roleRepository.findByLibelle(TypeDeRole.UTILISATEUR)
                .orElseThrow(() -> new RuntimeException("Role UTILISATEUR non trouvé"));

        roleUtilisateur = entityManager.find(Role.class, roleUtilisateur.getId());

        utilisateur.setRole(roleUtilisateur);
        log.info("role utilisateur "+roleUtilisateur);

        Utilisateur utilisateurPersiste = this.utilisateurRepository.save(utilisateur);
        log.info("validation et envoi pour enregistrement");
        this.validationService.enregistrer(utilisateurPersiste);
    }

    public void activation(Map<String, String> activation) {
        log.info("dans utilisateurService activation");
        Validation validation = this.validationService.lireEnFonctionDuCode(activation.get("code"));
        if(Instant.now().isAfter(validation.getExpiration())){
            throw  new RuntimeException("Votre code a expiré");
        }
        Utilisateur utilisateurActiver = this.utilisateurRepository.findById(validation.getUtilisateur().getIdUtilisateur()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        utilisateurActiver.setActif(true);
        this.utilisateurRepository.save(utilisateurActiver);
    }

    /* ================== RECHERCHER l'UTILISATEUR DANS LA BASE =============================================================*/
    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("dans utilisateurService loadUserByUsername");
        Utilisateur utilisateur = this.utilisateurRepository
                .findByUtilisateur(username)
                .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
        // recherche le role de m'utilisateur
        utilisateur.getRole().getLibelle();
        return utilisateur;
    }
}

