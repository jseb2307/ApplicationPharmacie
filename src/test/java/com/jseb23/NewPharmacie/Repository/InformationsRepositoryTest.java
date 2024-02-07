package com.jseb23.NewPharmacie.Repository;


import com.jseb23.NewPharmacie.Model.Informations;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;



@DataJpaTest /*configurer les tests d'intégration qui ciblent les couches de persistance de données*/
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) /*Cette annotation indique à Spring Boot de configurer automatiquement une base de données H2 intégrée pour les tests d'intégration*/
 class InformationsRepositoryTest
{
    @Autowired
    private InformationsRepository informationsRepository;

    /*==================== TEST SAUVEGARDER INFORMATIONS DANS BDD ==========*/
    @Test
    void InformationsRepository_Save_ReturnSavedInformations()
    {
        //Arrange
        Informations informations = Informations.builder()
                .numeroRue(7)
                .rue("rue de la poire")
                .codePostal(54200)
                .ville("toul")
                .numeroTelephone("0383231560")
                .mail("paris@gmail.com")
                .latitude("49,3")
                .longitude("5,2")
                .build();


        //Act
        Informations result = informationsRepository.save(informations);

        //Assert
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getIdInformations()).isGreaterThan(0);
    }

    /*====================TEST RECHERCHER INFORMATION PAR ID =================*/
    @Test
    void InformationsRepository_FindById_returnInformations() {
        //Arrange
        Informations informations = Informations.builder()

                .numeroRue(7)
                .rue("rue de la poire")
                .codePostal(54200)
                .ville("toul")
                .numeroTelephone("0383231560")
                .mail("paris@gmail.com")
                .latitude("49,3")
                .longitude("5,2")
                .build();


        //Act
        Informations result = informationsRepository.save(informations);

        //Assert
        Optional<Informations> foundInformationsOptional = informationsRepository.findById(result.getIdInformations());
        Assertions.assertThat(foundInformationsOptional).isPresent();// Vérifie que l'objet Optional contient une valeur
        Informations foundInformations = foundInformationsOptional.get(); // Obtient l'objet Informations à partir de l'Optional
        Assertions.assertThat(foundInformations.getIdInformations()).isEqualTo(result.getIdInformations()); // Vérifie que les ID sont égaux
    }

/* test mis a jour de la classe informations*/
    @Test
    void InformationsRepository_UpdateInformations() throws Exception {
        // Arrange
        Informations informations = Informations.builder()
                .numeroRue(7)
                .rue("rue de la poire")
                .codePostal(54200)
                .ville("toul")
                .numeroTelephone("0383231560")
                .mail("paris@gmail.com")
                .latitude("49,3")
                .longitude("5,2")
                .build();
        Informations savedInformations = informationsRepository.save(informations);

        // Act
        savedInformations.setNumeroRue(10);
        Informations updatedInformations = informationsRepository.save(savedInformations);

        // Assert
        Assertions.assertThat(updatedInformations.getNumeroRue()).isEqualTo(10);
        Assertions.assertThat(updatedInformations.getIdInformations()).isEqualTo(savedInformations.getIdInformations());
    }
/* test supprimer Information */
    @Test
    void InformationsRepository_DeleteInformations() {
        // Arrange
        Informations informations = Informations.builder()
                .numeroRue(7)
                .rue("rue de la poire")
                .codePostal(54200)
                .ville("toul")
                .numeroTelephone("0383231560")
                .mail("paris@gmail.com")
                .latitude("49,3")
                .longitude("5,2")
                .build();
        Informations savedInformations = informationsRepository.save(informations);

        // Act
        informationsRepository.deleteById(savedInformations.getIdInformations());

        // Assert
       Assertions.assertThat(informationsRepository.findById(savedInformations.getIdInformations())).isNotPresent();
    }


}
