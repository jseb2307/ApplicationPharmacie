package com.jseb23.NewPharmacie.Controller;

import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Service.InformationsService;
import com.jseb23.NewPharmacie.DTO.InformationsDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.jseb23.NewPharmacie.DTO.InformationsDTO.mapDTOToInformations;
import static com.jseb23.NewPharmacie.DTO.InformationsDTO.mapInformationsToDTO;


@RestController
@RequestMapping("/informations")
@AllArgsConstructor
public class InformationsController {


    private final InformationsService informationsService;

    @GetMapping("/{id}")
    public ResponseEntity<InformationsDTO> getInformationsServiceById(@PathVariable Long id) {
        Optional<Informations> informationsOptional = informationsService.findById(id);

        if (informationsOptional.isPresent()) {
            Informations informations = informationsOptional.get();
            InformationsDTO informationsDTO = mapInformationsToDTO(informations);
            return ResponseEntity.ok(informationsDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<InformationsDTO>> getAllPatients() {
        List<Informations> informations = informationsService.findAll();

        if (!informations.isEmpty()) {
            List<InformationsDTO> informationsDTOList = InformationsDTO.mapInformationsListToDTOList(informations);
            return ResponseEntity.ok(informationsDTOList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<InformationsDTO> createInformations(@RequestBody InformationsDTO informationsDTO) {
        Informations informations = mapDTOToInformations(informationsDTO);
        Informations createdInformations = informationsService.save(informations);
        InformationsDTO createdInformationsDTO = mapInformationsToDTO(createdInformations);

        return ResponseEntity.ok(createdInformationsDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Informations> updateInformations(@PathVariable Long id, @RequestBody Informations newInformations) {
        Optional<Informations> existingInformationsOptional = informationsService.findById(id);

        if (existingInformationsOptional.isPresent()) {
            Informations existingInformations = existingInformationsOptional.get();

            /*Mets à jour les propriétés nécessaires de l'entité existante avec les nouvelles valeurs*/
            existingInformations.setNumeroRue(newInformations.getNumeroRue());
            existingInformations.setRue(newInformations.getRue());
            existingInformations.setCodePostal(newInformations.getCodePostal());
            existingInformations.setVille(newInformations.getVille());
            existingInformations.setNumeroTelephone(newInformations.getNumeroTelephone());
            existingInformations.setMail(newInformations.getMail());

            /*Enregistre les modifications dans la base de données*/
            Informations updatedInformations = informationsService.save(existingInformations);

            return ResponseEntity.ok(updatedInformations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInformations(@PathVariable Long id) {
        Optional<Informations> existingInformations = informationsService.findById(id);

        if (existingInformations.isPresent()) {
            informationsService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
