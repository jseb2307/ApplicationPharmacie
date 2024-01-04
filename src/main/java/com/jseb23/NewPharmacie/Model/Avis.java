package com.jseb23.NewPharmacie.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level= AccessLevel.PRIVATE) // --> passe des attibuts private
@Entity
public class Avis
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;
     String message;
     String statut;


    @ManyToOne
    Utilisateur utilisateur;


}
