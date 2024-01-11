package com.jseb23.NewPharmacie.Controller;

import com.jseb23.NewPharmacie.DTO.AuthentificationDTO;
import com.jseb23.NewPharmacie.Model.Utilisateur;
import com.jseb23.NewPharmacie.Security.JwtService;
import com.jseb23.NewPharmacie.Service.UtilisateurService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateursController {

    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;
    private JwtService jwtService;

    @Autowired
    public UtilisateursController(AuthenticationManager authenticationManager,
                                  UtilisateurService utilisateurService,
                                  JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.utilisateurService = utilisateurService;
        this.jwtService = jwtService;
    }
    @PostMapping("/inscription")
    public void inscription(@RequestBody Utilisateur utilisateur) throws MessagingException {

        this.utilisateurService.inscription(utilisateur);
    }

    @GetMapping(path = "/activation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:63342")
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation) {
        //public ResponseEntity<String> activation(@RequestParam String utilisateur, @RequestParam String code) {
        try {
            log.info(" Application dans UtilisateurController -> Activation");

            log.debug("données activation reçues: {}", activation);

            this.utilisateurService.activation(activation);

            log.info("Activation a réussi");

            // Retourne une réponse HTTP 200 OK avec ce message
            return ResponseEntity.ok("Compte activé avec succès");

        } catch (Exception e) {

            log.error("Activation a échoué", e);

            // Retourne une réponse HTTP 500 Internal Server Error avec ce message d'erreur
            return ResponseEntity.status(500).body("Erreur lors de l'activation du compte");
        }
    }

    @PostMapping(path = "/connexion")
    public ResponseEntity<?> connexion(
            @RequestBody AuthentificationDTO authentificationDTO,
             @RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = MediaType.APPLICATION_JSON_VALUE)
             String contentType)
    {

        log.info(" application dans connexion");

        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
            );
            // Vérifiez que le Content-Type est bien "application/json"
            if (!MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
                log.error("Content-Type incorrect. Attendu : application/json, Reçu : {}", contentType);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requête mal formée. Content-Type incorrect.");
            }
            /**
             * renvoie le principal associé à l'authentification, qui peut être un objet utilisateur après une authentification réussie.
             * En vérifiant si le principal n'est pas nul, vous vous assurez que l'authentification a réussi.
             */
            if (authenticate.getPrincipal() != null)
            {
                log.info("Authentification réussie pour l'utilisateur : {}", authentificationDTO.username());

                Map<String, String> tokenMap = this.jwtService.generate(authentificationDTO.username());
                String token = tokenMap.get("bearer"); // génère la partie authentification du token

                if (token != null)
                {
                    log.info("Génération du token réussie pour l'utilisateur : {}", authentificationDTO.username());

                    return ResponseEntity.ok().body("connexion établie, token : " + token);// retourne le token formaté
                } else
                {
                    log.error("Erreur lors de la génération du token pour l'utilisateur : {}", authentificationDTO.username());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la génération du token");
                }
            }
        } catch (AuthenticationException e) {
            log.error("Erreur d'authentification pour l'utilisateur : {}", authentificationDTO.username(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentification échouée");
        }
        log.info("Non autorisé pour l'utilisateur : {}", authentificationDTO.username());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Vous n'êtes pas autorisé à vous connecter !");
    }
    //Gestion des requêtes OPTIONS
//    @RequestMapping(method = RequestMethod.OPTIONS)
//    public ResponseEntity<?> handleOptions() {
//        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS).build();
//    }
}