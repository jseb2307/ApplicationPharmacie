package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Utilisateur;
import com.jseb23.NewPharmacie.Model.Validation;
import com.jseb23.NewPharmacie.Repository.ValidationRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@AllArgsConstructor
@Service
@Slf4j
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;


    public void enregistrer(Utilisateur utilisateurs)
    {
        log.info("debut  methode enregistrement");
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateurs);
        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expiration = creation.plus(10, MINUTES); // Expiration = moment de la création plus 10 min
        validation.setExpiration(expiration);
        log.info("expiration"+ expiration);

        Random random = new Random(); // création du code à 6 chiffre
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validation.setCode(code);



        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);

        log.info("fin enregistrement");
    }

    public Validation lireEnFonctionDuCode(String code) { // retour validation
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }
}