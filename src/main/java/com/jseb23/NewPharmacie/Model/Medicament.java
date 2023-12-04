package com.jseb23.NewPharmacie.Model;

import com.jseb23.NewPharmacie.Model.Article;
import com.jseb23.NewPharmacie.Model.CategorieMedicament;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@ToString
@Data
@Entity
public class Medicament
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMedicament;

    @Column(length = 20, nullable = false)
    String libelleMedicament;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate dateMiseEnCirculation;

    @Column()
    int quantiteStock;

    @Column(length = 6, nullable = false)
    Double tarifMedicament;

    Boolean dispoSansOrdonnance;


    /*================= MAPPING ========================*/

    @ManyToOne
    @JoinColumn(name = "idArticle")
    Article article;

    @ManyToOne
    @JoinColumn(name = "idCategorieMedicament")
    CategorieMedicament categorieMedicament;


}
