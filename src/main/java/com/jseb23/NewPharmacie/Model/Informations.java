package com.jseb23.NewPharmacie.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Digits(integer = 5, fraction = 0, message = "Le numéro de rue doit être un entier")
            // bis, ter.., acceptés dans cette config
    int numeroRue;

    @Column(length = 60, nullable = false)
    @NotNull
    @NotBlank(message = "La rue ne peut pas être vide ou contenir uniquement des espaces blancs")
    @Size(min = 1, message = "La rue ne peut pas être vide")
    @Pattern(regexp = "\\D+")/* pas de chiffres*/
    String rue;

    @Column(length = 5)/* longueur dans la bdd*/
    @NotNull
    @Positive /* uniquement positif*/
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "Le numéro de rue doit être un entier")
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

   /* @Pattern(regexp = "\\d", message = "La latitude doit être un nombre")*/
    String latitude;


    /*@Pattern(regexp ="\\d", message = "La longitude doit être un nombre")*/
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


    void setIdInformations(Long idInformations) {
        this.idInformations = idInformations;
    }

    public void setNumeroRue(Integer numeroRue) throws IllegalArgumentException {
        // Vérification de la non-nullité
        if (numeroRue == null) throw new IllegalArgumentException("Le numéro de rue ne peut pas être null.");

        // Vérification de la non-vide et non-blanc
        if (numeroRue.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de rue ne peut pas être vide ou contenir uniquement des espaces blancs.");
        }

        // Vérification de la positivité
        if (numeroRue < 0) {
            throw new IllegalArgumentException("Le numéro de rue ne peut pas être négatif.");
        }

        // Vérification de la longueur maximale (5 chiffres)
        if (String.valueOf(numeroRue).length() > 5) {
            throw new IllegalArgumentException("Le numéro de rue ne peut pas dépasser 5 caractères.");
        }

        this.numeroRue = numeroRue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setDocteurs(List<Docteur> docteurs) {
        this.docteurs = docteurs;
    }

    public void setListeMutuelle(List<Mutuelle> listeMutuelle) {
        this.listeMutuelle = listeMutuelle;
    }

    public void setListePatient(List<Patient> listePatient) {
        this.listePatient = listePatient;
    }

    public void setPharmacie(Pharmacie pharmacie) {
        this.pharmacie = pharmacie;
    }

    public void setListeUtilisateurs(List<Utilisateur> listeUtilisateurs) {
        this.listeUtilisateurs = listeUtilisateurs;
    }
}
