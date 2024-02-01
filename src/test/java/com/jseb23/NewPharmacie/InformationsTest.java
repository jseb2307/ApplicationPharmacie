package com.jseb23.NewPharmacie;

import com.jseb23.NewPharmacie.Model.Informations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InformationsTest
{

    Informations informations;

    @Test
    void constructeurTest() throws Exception {
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

        assertAll("ConstructeurInformationsTest",
                () -> Assertions.assertEquals(7, informations.getNumeroRue()),
                () -> Assertions.assertEquals("rue de la poire", informations.getRue()),
                () -> Assertions.assertEquals(54200, informations.getCodePostal()),
                () -> Assertions.assertEquals("toul", informations.getVille()),
                () -> Assertions.assertEquals("0383231560", informations.getNumeroTelephone()),
                () -> Assertions.assertEquals("paris@gmail.com", informations.getMail()),
                () -> Assertions.assertEquals("49,3", informations.getLatitude()),
                () -> Assertions.assertEquals("5,2", informations.getLongitude())
        );
    }
    @ParameterizedTest
    @ValueSource(strings = {"" , "  " , "abc"})
    @NullSource
    void informationsNumeroRueTest(String numeroRue) {

            assertThrows(Exception.class, () -> {
                 informations = Informations.builder()
                        .numeroRue(Integer.parseInt(numeroRue))
                        .rue("rue de la poire")
                        .codePostal(54200)
                        .ville("toul")
                        .numeroTelephone("0383231560")
                        .mail("paris@gmail.com")
                        .latitude("49,3")
                        .longitude("5,2")
                        .build();
            });

    }
    @ParameterizedTest
    @ValueSource(strings = {"    "})
    @NullSource
    void informationsRueTest(String rue) {

        assertThrows(Exception.class, () -> {
            informations = Informations.builder()
                    .numeroRue(7)
                    .rue(rue)
                    .codePostal(54200)
                    .ville("toul")
                    .numeroTelephone("0383231560")
                    .mail("paris@gmail.com")
                    .latitude("49,3")
                    .longitude("5,2")
                    .build();
        });

    }








}

