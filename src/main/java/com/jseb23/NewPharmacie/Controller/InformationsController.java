package com.jseb23.NewPharmacie.Controller;

import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Model.Patient;
import com.jseb23.NewPharmacie.Service.InformationsService;
import com.jseb23.NewPharmacie.Service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/informations")
@AllArgsConstructor
public class InformationsController {


    private final InformationsService informationsService;

    @GetMapping("/{id}")
    public ResponseEntity<Informations> getinformationsServiceById(@PathVariable Long id) {
        Optional<Informations> informations = informationsService.findById(id);

        return informations.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Informations>> getAllPatients() {
        List<Informations> informations = informationsService.findAll();

        if (!informations.isEmpty()) {
            return ResponseEntity.ok(informations);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Informations> createInformations(@RequestBody Informations informations) {
        Informations createInformations = informationsService.save(informations);

        return ResponseEntity.ok(createInformations);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Informations> updateInformations(@PathVariable Long id, @RequestBody Informations informations) {
        Optional<Informations> existingInformations = informationsService.findById(id);

        if (existingInformations.isPresent()) {
            informations.setIdInformations(id);
            Informations updateInformations = informationsService.save(informations);
            return ResponseEntity.ok(updateInformations);
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
