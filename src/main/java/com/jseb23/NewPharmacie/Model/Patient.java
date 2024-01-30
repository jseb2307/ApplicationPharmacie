package com.jseb23.NewPharmacie.Model;

import com.fasterxml.jackson.annotation.*;
import com.jseb23.NewPharmacie.Model.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

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

}
