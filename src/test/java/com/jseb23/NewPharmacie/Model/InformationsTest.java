package com.jseb23.NewPharmacie.Model;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


class InformationsTest {

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

@Test
void ContextLoad(){}
    @Test
    void constructeurTest() throws Exception {

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
    @Test
    void getIdInformations()
    {
        /* insere un id dans le constructeur et le comparre*/
        // Arrange
        long testId = 1L;
        Informations informations = Informations.builder()
                .idInformations(testId)
                .build();

        // Act
        long actuelId = informations.getIdInformations();

        // Assert
        assertEquals(testId, actuelId);
    }

    @Test
    void getNumeroRue()
    {
      int testNumRue = 7;

      int numRueATester = informations.getNumeroRue();

      assertEquals(testNumRue,numRueATester);
    }

    @Test
    void getRue() {
    }

    @Test
    void getCodePostal() {
    }

    @Test
    void getVille() {
    }

    @Test
    void getNumeroTelephone() {
    }

    @Test
    void getMail() {
    }

    @Test
    void getLatitude() {
    }

    @Test
    void getLongitude() {
    }

    @Test
    void getDocteurs() {
    }

    @Test
    void getListeMutuelle() {
    }

    @Test
    void getListePatient() {
    }

    @Test
    void getPharmacie() {
    }

    @Test
    void getListeUtilisateurs() {
    }

    @Test
    void setIdInformations()
    {
        Long testId = 1L;

        informations.setIdInformations(testId);

        assertEquals(testId,informations.getIdInformations());
        assertNotNull(informations.getIdInformations());
    }

   @Test
    void setNumeroRue()  {

      // Test avec un numéro de rue négatif
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            informations.setNumeroRue(-1);
        }, "Le numéro de rue ne peut pas être négatif.");

        // Test avec un numéro de rue de plus de 5 caractères
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            informations.setNumeroRue(123456);
        }, "Le numéro de rue ne peut pas dépasser 5 caractères.");

        // Test avec un numéro de rue null
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            informations.setNumeroRue(null);
        }, "Le numéro de rue ne peut pas être null.");



    }

    @Test
    void setRue() {
    }

    @Test
    void setCodePostal() {
    }

    @Test
    void setVille() {
    }

    @Test
    void setNumeroTelephone() {
    }

    @Test
    void setMail() {
    }

    @Test
    void setLatitude() {
    }

    @Test
    void setLongitude() {
    }

    @Test
    void setDocteurs() {
    }

    @Test
    void setListeMutuelle() {
    }

    @Test
    void setListePatient() {
    }

    @Test
    void setPharmacie() {
    }

    @Test
    void setListeUtilisateurs() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }

    @Test
    void builder() {
    }
}