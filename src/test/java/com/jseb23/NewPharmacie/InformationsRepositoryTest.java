package com.jseb23.NewPharmacie;


import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Repository.InformationsRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;




@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InformationsRepositoryTest
{
    @Autowired
    private InformationsRepository informationsRepository;

    /* ================ TEST Sauvegarde Informations =====================*/
    @Test
    public void InformationRepository_Save_ReturnSavedInformation()
    {

        /*Arrange*/
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

        /*Act*/
        Informations savedInformations = informationsRepository.save(informations);

        /*Assert*/
        Assertions.assertThat(savedInformations).isNotNull();
        Assertions.assertThat(savedInformations.getIdInformations()).isGreaterThan(0);
    }




}
