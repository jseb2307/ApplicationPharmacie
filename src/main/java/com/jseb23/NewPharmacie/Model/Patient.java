package com.jseb23.NewPharmacie.Model;

import com.fasterxml.jackson.annotation.*;
import com.jseb23.NewPharmacie.Model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.checkerframework.common.value.qual.MatchesRegex;

import java.time.LocalDate;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Data
@Entity
@Accessors(chain = true)  /* cette annotation pour personnalise le nom du getter*/
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPatient;

    @Column(length = 20, nullable = false)
    String nomPatient;

    @Column(length = 20, nullable = false)
    String prenomPatient;

    @Temporal(TemporalType.DATE)
    LocalDate dateDeNaissance;

    @Column(length = 15)
    String numeroSecuPatient;

    /*========================= MAPPING ==========================*/
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idInformations")
    Informations informations;

    @ManyToOne
    @JoinColumn(name = "idMutuelle")
    Mutuelle mutuelle;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<Ordonnance> listOrdonnances;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "patient_docteur",
            joinColumns = @JoinColumn(name = "idPatient"),
            inverseJoinColumns = @JoinColumn(name = "idDocteur")
    )
    List<Docteur> listDocteurs;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    Panier panier;


    public void setIdPatient(Long idPatient)
    {
        this.idPatient = idPatient;
    }

    public void setNomPatient(String nomPatient) throws IllegalArgumentException{
        // Vérification de la non-nullité
        if (nomPatient == null) throw new IllegalArgumentException("Le nom du patient ne peut pas être null.");

        // Vérification de la non-vide et non-blanc
        if (nomPatient.toString().trim().isEmpty()) throw new IllegalArgumentException("Le nom du patient ne peut pas être vide ou contenir uniquement des espaces blancs.");

        // Vérification des lettres uniquement
        if (!nomPatient.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Le nom du patient ne peut contenir que des lettres.");
        }

        this.nomPatient = nomPatient;
    }


    @NotEmpty
    @NotBlank
    public void setPrenomPatient(String prenomPatient)
    {

        // Vérification de la non-nullité
        if (prenomPatient == null) throw new IllegalArgumentException("Le prénom du patient ne peut pas être null.");

        // Vérification de la non-vide et non-blanc
       // if (prenomPatient.toString().trim().isEmpty()) throw new IllegalArgumentException("Le prénom du patient ne peut pas être vide ou contenir uniquement des espaces blancs.");

        // Vérification des lettres uniquement
        if (!prenomPatient.matches("^[a-zA-Z]+$")) throw new IllegalArgumentException("Le nom du patient ne peut contenir que des lettres.");


        this.prenomPatient = prenomPatient;
    }


    @NotEmpty
    @NotBlank
    @Max(11)
    public void setDateDeNaissance(@MatchesRegex(value = "^\\d{4}-\\d{2}-\\d{2}$")LocalDate dateDeNaissance) throws IllegalArgumentException
    {
        // Vérification de la non-nullité
        if (dateDeNaissance == null) throw new IllegalArgumentException("La date ne peut pas être null.");

        // Vérification de la non-vide et non-blanc
        if (dateDeNaissance.toString().trim().isEmpty()) {
            throw new IllegalArgumentException("La date ne peut être vide !.");
        }

        // Vérification de l'absence de lettres
        if (dateDeNaissance.toString().matches(".*[a-zA-Z].*")) throw new IllegalArgumentException("La date ne peut pas contenir de lettres.");

        // Vérification du nombre de caractères
        if (dateDeNaissance.toString().length() >= 11) throw new IllegalArgumentException("La date ne peut pas dépasser 10 caractères.");



        this.dateDeNaissance = dateDeNaissance;
    }

    public void setNumeroSecuPatient(String numeroSecuPatient) {
        this.numeroSecuPatient = numeroSecuPatient;
    }

    public void setInformations(Informations informations) {
        this.informations = informations;
    }

    public void setMutuelle(Mutuelle mutuelle) {
        this.mutuelle = mutuelle;
    }

    public void setListOrdonnances(List<Ordonnance> listOrdonnances) {
        this.listOrdonnances = listOrdonnances;
    }

    public void setListDocteurs(List<Docteur> listDocteurs) {
        this.listDocteurs = listDocteurs;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }
}
