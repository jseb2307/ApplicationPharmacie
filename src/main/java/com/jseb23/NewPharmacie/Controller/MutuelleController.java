package com.jseb23.NewPharmacie.Controller;


import com.jseb23.NewPharmacie.DTO.DocteurDTO;
import com.jseb23.NewPharmacie.DTO.InformationsDTO;
import com.jseb23.NewPharmacie.DTO.MutuelleDTO;
import com.jseb23.NewPharmacie.Model.Docteur;
import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Model.Mutuelle;
import com.jseb23.NewPharmacie.Service.InformationsService;
import com.jseb23.NewPharmacie.Service.MutuelleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jseb23.NewPharmacie.DTO.DocteurDTO.mapDocteurToDTO;
import static com.jseb23.NewPharmacie.DTO.MutuelleDTO.mapMutuelleToDTO;

@RestController
@RequestMapping("/mutuelle")
@AllArgsConstructor

public class MutuelleController
{

    private final MutuelleService mutuelleService;
    private final InformationsService informationsService;


    /*======================================= MUTUELLE PAR ID ==========================================================*/
    @GetMapping("/{id}")
    public ResponseEntity<MutuelleDTO> getMutelleById(@PathVariable Long id) {
        Optional<Mutuelle> mutelle = mutuelleService.findById(id);

        return mutelle.map(m -> ResponseEntity.ok(mapMutuelleToDTO(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /*======================================= LISTE DES MUTUELLES ======================================================*/
    @GetMapping("/all")
    public ResponseEntity<List<MutuelleDTO>> getAllMutuelles()
    {
        try {
            List<Mutuelle> mutuelles = mutuelleService.findAll();

            if (!mutuelles.isEmpty()) {
                List<MutuelleDTO> mutuelleDTOS = mutuelles.stream()
                        .map(MutuelleDTO::mapMutuelleToDTO)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(mutuelleDTOS);
            } else {
                return ResponseEntity.noContent().build();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*======================================= CREER MUTUELLE  ========================================================*/
    @PostMapping("/create")
    public ResponseEntity<Mutuelle> createMutuelle(@RequestBody MutuelleDTO mutuelleDTO) {
        try {
            // Créez l'information à partir de DTO
            Informations informations = InformationsDTO.mapDTOToInformations(mutuelleDTO.getInformations());

            // Enregistrez l'information dans la base de données pour récupérer l'ID
            informations = informationsService.save(informations);

            // Créez la mutuelle avec l'ID de l'information
            Mutuelle mutuelle = new Mutuelle();
            mutuelle.setNomMutuelle(mutuelleDTO.getNomMutuelle());
            mutuelle.setInformations(informations);
            // autres attributs du Docteur si nécessaire

            // Enregistrez le docteur dans la base de données
            mutuelle = mutuelleService.save(mutuelle);

            return ResponseEntity.ok(mutuelle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /*======================================= MODIFIER MUTUELLE  ======================================================*/
    @PutMapping("/update/{id}")
    public ResponseEntity<MutuelleDTO> updateMutuelle(@PathVariable Long id, @RequestBody Mutuelle mutuelle) {
        Optional<Mutuelle> existingMutuelle = mutuelleService.findById(id);

        if (existingMutuelle.isPresent()) {
            mutuelle.setIdMutuelle(id);
            Mutuelle updatedMutuelle = mutuelleService.save(mutuelle);
            MutuelleDTO updatedMutuelleDTO = mapMutuelleToDTO(updatedMutuelle);
            return ResponseEntity.ok(updatedMutuelleDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*======================================= SUPPRIMER MUTUELLE  ========================================================*/
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMutuelle(@PathVariable Long id) {
        Optional<Mutuelle> existingMutuelle = mutuelleService.findById(id);

        if (existingMutuelle.isPresent()) {
            mutuelleService.deleteById(id);
            return ResponseEntity.ok("Mutuelle with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
