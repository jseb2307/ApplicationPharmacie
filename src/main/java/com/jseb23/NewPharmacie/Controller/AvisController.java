package com.jseb23.NewPharmacie.Controller;

import com.jseb23.NewPharmacie.Model.Avis;
import com.jseb23.NewPharmacie.Service.AvisService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avis")
@Data
public class AvisController
{
    private final AvisService avisService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/creer")
    public void creer(@RequestBody Avis avis)
    {
        this.avisService.creer(avis);
    }
}
