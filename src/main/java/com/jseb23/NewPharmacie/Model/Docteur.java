package com.jseb23.NewPharmacie.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE) // --> passe des attibuts private
@Entity
public class Docteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //--> auto incrémentation par la base de données
    Long idDocteur;

    @Column(length = 15, nullable = false)
    String nomDocteur;

    @Column(length = 20, nullable = false)
    String prenomDocteur;

    /*===================== MAPPING =====================*/
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idInformations")
    Informations informations;

    @JsonBackReference
    @ManyToMany(mappedBy = "listDocteurs", cascade = CascadeType.ALL)
    List<SpecialiteDocteur> listSpecialiteDocteurs;

    @OneToMany(mappedBy = "docteur", cascade = CascadeType.ALL)
    List<Ordonnance> listOrdonnances;


    @ManyToMany(mappedBy = "listDocteurs")
    private List<Patient> listPatients;


}
