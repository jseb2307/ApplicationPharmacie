package com.jseb23.NewPharmacie;


import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Repository.InformationsRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;




@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InformationsRepositoryTest
{
    @Autowired
    private InformationsRepository informationsRepository;

    @Test
    void InformationsRepository_SaveAll_ReturnSavedInformations()
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
        Informations savedInformations = informationsRepository.save(informations);

        //Assert
        Assertions.assertThat(savedInformations).isNotNull();
        Assertions.assertThat(savedInformations.getIdInformations()).isGreaterThan(0);
    }


}