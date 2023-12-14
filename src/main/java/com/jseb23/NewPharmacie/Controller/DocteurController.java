package com.jseb23.NewPharmacie.Controller;

import com.jseb23.NewPharmacie.DTO.DocteurDTO;
import com.jseb23.NewPharmacie.DTO.InformationsDTO;
import com.jseb23.NewPharmacie.Model.Docteur;
import com.jseb23.NewPharmacie.Service.DocteurService;
import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Service.InformationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.jseb23.NewPharmacie.DTO.DocteurDTO.mapDocteurToDTO;


@RestController
@RequestMapping("/docteur")
@AllArgsConstructor
public class DocteurController
{

    private final DocteurService docteurService;
    private final InformationsService informationsService;

    @GetMapping("/{id}")
    public ResponseEntity<DocteurDTO> getDocteurById(@PathVariable Long id) {
        Optional<Docteur> docteur = docteurService.findById(id);

        return docteur.map(d -> ResponseEntity.ok(mapDocteurToDTO(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
/*======================================= LISTE DES DOCTEURS ==========================================================*/
    @GetMapping("/all")
    public ResponseEntity<List<DocteurDTO>> getAllDocteurs()
    {
        try {
            List<Docteur> docteurs = docteurService.findAll();

            if (!docteurs.isEmpty()) {
                List<DocteurDTO> docteurDTOS = docteurs.stream()
                        .map(DocteurDTO::mapDocteurToDTO)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(docteurDTOS);
            } else {
                return ResponseEntity.noContent().build();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Docteur> createDocteur(@RequestBody DocteurDTO docteurDTO) {
        try {
            // Créez l'information à partir de DTO
            Informations informations = InformationsDTO.mapDTOToInformations(docteurDTO.getInformations());

            // Enregistrez l'information dans la base de données pour récupérer l'ID
            informations = informationsService.save(informations);

            // Créez le docteur avec l'ID de l'information
            Docteur docteur = new Docteur();
            docteur.setNomDocteur(docteurDTO.getNomDocteur());
            docteur.setPrenomDocteur(docteurDTO.getPrenomDocteur());
            docteur.setInformations(informations);
                    // autres attributs du Docteur si nécessaire

            // Enregistrez le docteur dans la base de données
            docteur = docteurService.save(docteur);

            return ResponseEntity.ok(docteur);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DocteurDTO> updateDocteur(@PathVariable Long id, @RequestBody Docteur docteur) {
        Optional<Docteur> existingDocteur = docteurService.findById(id);

        if (existingDocteur.isPresent()) {
            docteur.setIdDocteur(id);
            Docteur updatedDocteur = docteurService.save(docteur);
            DocteurDTO updatedDocteurtDTO = mapDocteurToDTO(updatedDocteur);
            return ResponseEntity.ok(updatedDocteurtDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocteur(@PathVariable Long id) {
        Optional<Docteur> existingDocteur = docteurService.findById(id);

        if (existingDocteur.isPresent()) {
            docteurService.deleteById(id);
            return ResponseEntity.ok("Docteur with ID " + id + " deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }







}
