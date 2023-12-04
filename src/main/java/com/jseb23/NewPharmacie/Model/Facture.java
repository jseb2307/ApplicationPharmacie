package com.jseb23.NewPharmacie.Model;

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
public class Facture
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idFacture;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate dateFacture;

    @Column(length = 6)
    Double montantTotal;

    @OneToOne
    @JoinColumn(name = "idPanier", unique = true)
     Panier panier;

    @ManyToOne
    @JoinColumn(name = "idPharmacie")
    Pharmacie pharmacie;

}
