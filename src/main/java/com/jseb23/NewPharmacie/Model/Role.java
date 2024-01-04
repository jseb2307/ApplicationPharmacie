package com.jseb23.NewPharmacie.Model;

import com.jseb23.NewPharmacie.TypeDeRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;



@Entity
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    TypeDeRole libelle;
}