package com.jseb23.NewPharmacie.DTO;

import com.jseb23.NewPharmacie.Model.Docteur;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.jseb23.NewPharmacie.DTO.SpecialiteDocteurDTO.getSpecialitesDTO;

@Data
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class DocteurDTO
{
    Long idDocteur;
    String nomDocteur;
    String prenomDocteur;
    List<SpecialiteDocteurDTO> listSpecialites;
    InformationsDTO informations;


    public static DocteurDTO mapDocteurToDTO(Docteur docteur)
    {
        InformationsDTO informationsDTO = docteur.getInformations() != null ?
                InformationsDTO.mapInformationsToDTO(docteur.getInformations()) :
                null;

        return new DocteurDTO(
                docteur.getIdDocteur(),
                docteur.getNomDocteur(),
                docteur.getPrenomDocteur(),
                getSpecialitesDTO(docteur.getListSpecialiteDocteurs()),
                informationsDTO
        );
    }

    public static List<DocteurDTO> getDocteursDTO(List<Docteur> listDocteurs) {
        if (listDocteurs != null && !listDocteurs.isEmpty()) {
            return listDocteurs.stream()
                    .map(docteur -> new DocteurDTO(
                            docteur.getIdDocteur(),
                            docteur.getNomDocteur(),
                            docteur.getPrenomDocteur(),
                            getSpecialitesDTO(docteur.getListSpecialiteDocteurs()),
                            InformationsDTO.mapInformationsToDTO(docteur.getInformations())
                    ))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList(); // La liste des docteurs est vide ou null
    }




}
