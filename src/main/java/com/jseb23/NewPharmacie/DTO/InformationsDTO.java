package com.jseb23.NewPharmacie.DTO;

import com.jseb23.NewPharmacie.Model.Informations;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@AllArgsConstructor
public class InformationsDTO
{
         Long idInformations;
         int numeroRue;
         String rue;
         int codePostal;
         String ville;
         String numeroTelephone;
         String mail;

    public static InformationsDTO mapInformationsToDTO(Informations informations) {
        return new InformationsDTO(
                informations.getIdInformations(),
                informations.getNumeroRue(),
                informations.getRue(),
                informations.getCodePostal(),
                informations.getVille(),
                informations.getNumeroTelephone(),
                informations.getMail()
        );
    }

    public static List<InformationsDTO> mapInformationsListToDTOList(List<Informations> informationsList)
    {
        return informationsList.stream()
                .map(InformationsDTO::mapInformationsToDTO)
                .collect(Collectors.toList());
    }

    public static Informations mapDTOToInformations(InformationsDTO informationsDTO) {
        Informations informations = new Informations();
        informations.setNumeroRue(informationsDTO.getNumeroRue());
        informations.setRue(informationsDTO.getRue());
        informations.setCodePostal(informationsDTO.getCodePostal());
        informations.setVille(informationsDTO.getVille());
        informations.setNumeroTelephone(informationsDTO.getNumeroTelephone());
        informations.setMail(informationsDTO.getMail());
        return informations;
    }

}

