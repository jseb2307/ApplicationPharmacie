package com.jseb23.NewPharmacie.Controller;


import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Repository.InformationsRepository;
import com.jseb23.NewPharmacie.Security.JwtFilter;
import com.jseb23.NewPharmacie.Security.JwtService;
import com.jseb23.NewPharmacie.Service.InformationsService;
import com.jseb23.NewPharmacie.Service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InformationsController.class)
@AutoConfigureMockMvc

public class InformationsControllerTest
{
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private InformationsRepository informationsRepository;
    @MockBean
    private InformationsService informationsService;
    @MockBean
    private UtilisateurService utilisateurService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private JwtFilter jwtFilter;

    @InjectMocks
    private InformationsController informationsController;

    ObjectMapper objectMapper = new ObjectMapper(); // mapper l'objet en json
    ObjectWriter objectWriter = objectMapper.writer();


    Informations informations = Informations.builder()
            .idInformations(1L)
            .numeroRue(7)
            .rue("rue de la poire")
            .codePostal(54200)
            .ville("toul")
            .numeroTelephone("0383231560")
            .mail("paris@gmail.com")
            .latitude("49,3")
            .longitude("5,2")
            .build();

    Informations informations2 = Informations.builder()
            .idInformations(2L)
            .numeroRue(8)
            .rue("rue de la côte")
            .codePostal(54170)
            .ville("flirey")
            .numeroTelephone("0383231632")
            .mail("flirey@gmail.com")
            .latitude("47,2")
            .longitude("8,2")
            .build();

    @Test
    @WithMockUser // simule un utilisateur authentifié par spring sécurity
    void getAllInformations_ReturnInformationsList() throws Exception {
        List<Informations> listeInformations = new ArrayList<>(Arrays.asList(informations, informations2));

        Mockito.when(informationsRepository.findAll()).thenReturn(listeInformations);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/informations/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



  /*  @Test
    @WithMockUser
    void createInformations_ReturnCreatedInformations() throws Exception {
        // Arrange
        Informations informations = Informations.builder()
                .idInformations(4L)
                .numeroRue(7)
                .rue("rue de la poire")
                .codePostal(54200)
                .ville("toul")
                .numeroTelephone("0383231560")
                .mail("paris@gmail.com")
                .latitude("49,3")
                .longitude("5,2")
                .build();


        Mockito.when(informationsService.save(Mockito.any(Informations.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/informations/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(informations));

        System.out.println("Request Body: " + objectMapper.writeValueAsString(informations));

        // Assert
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$.idInformations").doesNotExist()) // Assuming id is not returned in response
              //  .andExpect(jsonPath("$.numeroRue").value(informationsDTO.getNumeroRue()))
               // .andExpect(jsonPath("$.rue").value(informationsDTO.getRue()))
               // .andExpect(jsonPath("$.codePostal").value(informationsDTO.getCodePostal()))
               // .andExpect(jsonPath("$.ville").value(informationsDTO.getVille()))
                //.andExpect(jsonPath("$.numeroTelephone").value(informationsDTO.getNumeroTelephone()))
              //  .andExpect(jsonPath("$.mail").value(informationsDTO.getMail()))
               // .andExpect(jsonPath("$.latitude").value(informationsDTO.getLatitude()))
              //  .andExpect(jsonPath("$.longitude").value(informationsDTO.getLongitude()));
    }

    @Test
    void updateInformations() {
    }

    @Test
    @WithMockUser
    void deleteInformations_ExistingId_ReturnNoContent() throws Exception {
        Long idToDelete = 1L;

        Mockito.when(informationsService.findById(idToDelete)).thenReturn(Optional.of(new Informations()));

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/informations/delete/{id}", idToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }*/

}