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
public class Article
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idArticle;

    @Column(length = 2, nullable = false)
    int quantiteArticle;

    @ManyToOne
    @JoinColumn(name = "idPanier")
    Panier panier;

    @OneToMany(mappedBy = "article")
    List<Medicament>listMedicaments;
}
