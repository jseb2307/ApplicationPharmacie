package com.jseb23.NewPharmacie.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Validation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idValidation;
    Instant creation;
    Instant expiration;
    Instant activation;
    String code;

    /*============== MAPPING ============================*/

    @OneToOne(cascade = CascadeType.ALL)
    Utilisateur utilisateur;
}
