package com.jseb23.NewPharmacie.Model;

import com.jseb23.NewPharmacie.TypeDeRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    TypeDeRole libelle;


    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT) //Hibernate utilise une seule requête subselect pour charger l'intégralité de la collection utilisateurs.
    private List<Utilisateur> utilisateurs = new ArrayList<>();
}