package com.jseb23.NewPharmacie.Model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Entity

public class Mutuelle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMutuelle;

    @Column(length = 40, nullable = false)
    String nomMutuelle;

    /*========================= MAPPING ==========================*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idInformations")

    Informations informations;

    @OneToMany(mappedBy = "mutuelle")
    private List<Patient> listPatients;
 /* essai 2 */
}

