package com.jseb23.NewPharmacie.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Data
@Entity
public class Panier
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPanier;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate datePanier;

    /*======================== MAPPING =========================*/
    @OneToOne
    @JoinColumn(name = "idPatient", unique = true)
    Patient patient;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL)
    List<Ordonnance> listOrdonnances;

    @OneToOne(mappedBy = "panier", cascade = CascadeType.ALL)
    Facture facture;

    @OneToMany(mappedBy = "panier",cascade = CascadeType.ALL)
    List<Article>listArticles;

}
