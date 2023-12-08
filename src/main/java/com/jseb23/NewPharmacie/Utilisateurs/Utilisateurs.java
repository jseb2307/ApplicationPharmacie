package com.jseb23.NewPharmacie.Utilisateurs;
import com.jseb23.NewPharmacie.Model.Informations;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Data
@Entity
public class Utilisateurs
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUtilisateur;

    @NotNull
    @NotBlank
    @NotEmpty
    String nomUtilisateur;

    @NotNull
    @NotBlank
    @NotEmpty
    String prenomUtilisateur;

    @NotNull
    @NotBlank
    @NotEmpty
    String utilisateur;

    @NotNull
    @NotBlank
    @NotEmpty
    String motDePasseUtilisateur;

    /*========================= MAPPING ==========================*/


    @ManyToOne
    @JoinColumn(name = "idInformations", referencedColumnName = "idInformations")
    @JsonBackReference
    Informations informations;





}
