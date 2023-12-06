package com.jseb23.NewPharmacie.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
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

    @ManyToOne
    @JoinColumn(name = "idPatient")
    @JsonIgnore
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "idPanier")
    @JsonIgnore
    Panier panier;


}
