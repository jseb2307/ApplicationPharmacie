package com.jseb23.NewPharmacie.Controller;

import com.jseb23.NewPharmacie.DTO.AuthentificationDTO;
import com.jseb23.NewPharmacie.Model.Utilisateur;
import com.jseb23.NewPharmacie.Security.JwtService;
import com.jseb23.NewPharmacie.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public void inscription(@RequestBody Utilisateur utilisateur) {

        this.utilisateurService.inscription(utilisateur);
    }

    @PostMapping(path = "/activation")
    public void activation(@RequestBody Map<String, String> activation) {
        log.info("Activation");
        this.utilisateurService.activation(activation);
    }

    @PostMapping(path = "/connexion")
    public ResponseEntity<?> connexion(@RequestBody AuthentificationDTO authentificationDTO) {

        log.info(" application dans connexion");

        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
            );

            if(authenticate.isAuthenticated())
            {
                Map<String, String> tokenMap = this.jwtService.generate(authentificationDTO.username());
                String token = tokenMap.get("bearer"); // Récupérez le token de la map
                if (token != null) {
                    return ResponseEntity.ok().body("connexion établie, token : " + token);// récupère le token formaté
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la génération du token");
                }
            }
        } catch (AuthenticationException e) {
            log.error("Erreur d'authentification", e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Non autorisé");
    }
    //Gestion des requêtes OPTIONS
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS).build();
    }
}