package com.jseb23.NewPharmacie.Model;


import com.jseb23.NewPharmacie.Model.Facture;
import com.jseb23.NewPharmacie.Model.Informations;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Entity
public class Pharmacie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPharmacie;

    @Column(length = 20, nullable = false)
    String nomPharmacie;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idInformations")
    Informations informations;

    @OneToMany(mappedBy = "pharmacie", cascade = CascadeType.ALL)
    private List<Facture> factures;



    public void setNomPharmacie(String nomPharmacie) {
        this.nomPharmacie = nomPharmacie;
    }
}
