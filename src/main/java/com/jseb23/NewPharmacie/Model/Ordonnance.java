package com.jseb23.NewPharmacie.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Entity
public class Ordonnance
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idOrdonnance;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate dateOrdonnance;


    /*====================== MAPPING ======================*/
    @ManyToOne
    @JoinColumn(name = "idDocteur")
    Docteur docteur;

//    @ManyToOne
//    @JoinColumn(name = "idPatient")
//    Patient patient;

    @ManyToOne
    @JoinColumn(name = "idPanier")
    Panier panier;

    /**
     * SETTER
     * @param dateOrdonnance
     */
    public void setDateOrdonnance(LocalDate dateOrdonnance) {
        this.dateOrdonnance = dateOrdonnance;
    }
}
