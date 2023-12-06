package com.jseb23.NewPharmacie.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Email;

import java.util.List;

@AllArgsConstructor //(staticName="of") -->
@NoArgsConstructor // -> Constructeur sans argument
@Builder  //-> permet un constructeur à la volée
@FieldDefaults(level=AccessLevel.PRIVATE) // --> passe des attibuts private
@ToString(of= {"idInformations","numeroRue","rue","codePostal"})

@Data
@Entity // ---------> partie BDD
public class Informations
{
    /**
     * declaration attributs
     */
    @Id //-> clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) //--> auto incrémentation par la base de données
    Long idInformations;

    @Column(length = 3, nullable = false)
    int numeroRue;

    @Column(length = 60, nullable = false)
    String rue;

    @Column(length = 5)
    @NotNull
    int codePostal;

    @Column(length = 20, nullable = false)
    String ville;

    @Column(length = 10)
    String numeroTelephone;

    @Email
    @Column(length = 30)
    String mail;

    @JsonBackReference
    @OneToMany(mappedBy = "informations")
    List<Docteur> docteurs;

    @JsonBackReference
    @OneToMany(mappedBy = "informations")
    List<Mutuelle> listeMutuelle;

    @JsonBackReference
    @OneToMany(mappedBy = "informations")
    List<Patient> listePatient;

    @OneToOne(mappedBy = "informations")
    Pharmacie pharmacie;


}
