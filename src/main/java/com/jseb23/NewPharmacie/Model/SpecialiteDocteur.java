package com.jseb23.NewPharmacie.Model;

import com.jseb23.NewPharmacie.Model.Docteur;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Entity
public class SpecialiteDocteur
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idSpecialiteDocteur;

    @Column(length = 20, nullable = false)
    String nomSpecialiteDocteur;

    /*===================== MAPPING =====================*/

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "SpecialiteDuDocteur",
            joinColumns = @JoinColumn(name = "idSpecialiteDocteur"),
            inverseJoinColumns = @JoinColumn(name = "idDocteur")
    )
    List<Docteur> listDocteurs = new ArrayList<>();


}
