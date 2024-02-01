package com.jseb23.NewPharmacie.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Email;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor /*-> Constructeur sans argument*/
@Builder  /*-> permet un constructeur à la volée*/
@FieldDefaults(level=AccessLevel.PRIVATE) /* --> passe les attibuts private*/
@ToString(of= {"idInformations","numeroRue","rue","codePostal"})

@Data
@Entity
public class Informations
{
    /**
     * declaration attributs
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /*--> auto incrémentation par la base de données*/
    Long idInformations;

    @Column(length = 3, nullable = false)
    @NotNull
     @Positive
    @Pattern(regexp = "\\d+")/* interdit les lettres*/
            // bis, ter.., acceptés dans cette config
    int numeroRue;

    @Column(length = 60, nullable = false)
    @NotNull
    @Pattern(regexp = "\\D+")/* pas de chiffres*/
    String rue;

    @Column(length = 5)/* longueur dans la bdd*/
    @NotNull
    @Digits(integer = 5, fraction = 0)/*nombre de 5 chiffres sans décimale*/
    @Positive /* uniquement positif*/
    @Pattern(regexp = "\\d+")/* interdit les lettres*/
    int codePostal;

    @Column(length = 50, nullable = false)
    @NotNull
    @Pattern(regexp = "\\D+")/* pas de chiffres*/
            String ville;

    @Column(length = 10)
    @Pattern(regexp = "^(0|\\+33)[1-9]([-. ]?[0-9]{2}){4}$")
    String numeroTelephone;

    @Column(length = 30)
    @Email
    String mail;

    @Pattern(regexp = "\\d+")/* interdit les lettres*/
    String latitude;


    @Pattern(regexp = "\\d+")/* interdit les lettres*/
    String longitude;

/*===================== MAPPING ====================*/
    @OneToMany(mappedBy = "informations")
    List<Docteur> docteurs;

    @OneToMany(mappedBy = "informations")
    List<Mutuelle> listeMutuelle;

    @OneToMany(mappedBy = "informations")
    List<Patient> listePatient;

    @OneToOne
    @JoinColumn(name = "idPharmacie", referencedColumnName = "idPharmacie", unique = true)
    Pharmacie pharmacie;

    @OneToMany(mappedBy = "informations")
    List<Utilisateur> listeUtilisateurs;


}
