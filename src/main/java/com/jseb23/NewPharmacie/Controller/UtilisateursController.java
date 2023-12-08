package com.jseb23.NewPharmacie.Controller;

import com.jseb23.NewPharmacie.Service.UtilisateursService;
import com.jseb23.NewPharmacie.Utilisateurs.Utilisateurs;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/utilisateurs")
@AllArgsConstructor
public class UtilisateursController {


    private final UtilisateursService utilisateursService;

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateurs> getUtilisateursById(@PathVariable Long id) {
        Optional<Utilisateurs> utilisateur = utilisateursService.findById(id);

        return utilisateur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Utilisateurs>> getAllUtilisateurs() {
        List<Utilisateurs> utilisateurs = utilisateursService.findAll();

        if (!utilisateurs.isEmpty()) {
            return ResponseEntity.ok(utilisateurs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Utilisateurs> createUtilisateur(@RequestBody Utilisateurs utilisateur) {
        Utilisateurs createUtilisateur = utilisateursService.save(utilisateur);

        return ResponseEntity.ok(createUtilisateur);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Utilisateurs> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateurs utilisateur) {
        Optional<Utilisateurs> existingPatient = utilisateursService.findById(id);

        if (existingPatient.isPresent()) {
            utilisateur.setIdUtilisateur(id);
            Utilisateurs updatedPatient = utilisateursService.save(utilisateur);
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        Optional<Utilisateurs> existingPatient = utilisateursService.findById(id);

        if (existingPatient.isPresent()) {
            utilisateursService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

