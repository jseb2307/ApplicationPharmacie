package com.jseb23.NewPharmacie.Model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Data
@Entity
public class CategorieMedicament
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCategorieMedicament;

    @Column(length = 25, nullable = false)
    String libelleCategorieMedicament;


    /*====================== MAPPING =============================*/
    @OneToMany(mappedBy = "categorieMedicament")
    List<Medicament> listMedicaments;

}

