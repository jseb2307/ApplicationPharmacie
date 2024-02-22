package com.jseb23.NewPharmacie.Model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.common.value.qual.MatchesRegex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest
{
    Patient patient = Patient.builder()
            .idPatient(1L)
            .nomPatient("franck")
            .prenomPatient("jean")
            .dateDeNaissance(LocalDate.ofEpochDay(1980-12-12))
            .numeroSecuPatient("180125452806942")
            .build();


    @Test
    void ContextLoad(){}
    @Test
    void constructeurPatientTest() throws Exception {

        assertAll("ConstructeurIPatientTest",
                () -> Assertions.assertEquals(1L,patient.getIdPatient()),
                () -> Assertions.assertEquals("franck",patient.getNomPatient()),
                () -> Assertions.assertEquals("jean",patient.getPrenomPatient()),
                () -> Assertions.assertEquals(LocalDate.ofEpochDay(1980-12-12),patient.getDateDeNaissance()),
                () -> Assertions.assertEquals("180125452806942",patient.getNumeroSecuPatient())
        );
    }

    @Test
    @DisplayName("Test getId patient")
    void getIdPatient()
    {
        /* insere un id dans le constructeur et le comparre*/
        // Arrange
        long testId = 1L;
        Patient patient = Patient.builder()
                .idPatient(testId)
                .build();

        // Act
        long actuelId = patient.getIdPatient();

        // Assert
        assertEquals(testId, actuelId);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "123", "!@#"})
    @NullSource
    @DisplayName("Tests set  nom du patient")
    void setNomPatient(String nomPatient)
    {
        assertThrows
                (
                      IllegalArgumentException.class,
                         () -> patient.setNomPatient(nomPatient)
                  );

    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "123", "!@#"})
    @NullSource
    @DisplayName("Tests set  prénom du patient")
    void setPrenomPatient(String prenomPatient)
    {
        assertThrows
                (
                        IllegalArgumentException.class,
                        () -> patient.setPrenomPatient(prenomPatient)
                );

    }




    @DisplayName("Tests set  date de naissance du patient")
    void testSetDateNaissancePatient(LocalDate dateNaissancePatient) {
    }
}

