package com.jseb23.NewPharmacie.DTO;

import com.jseb23.NewPharmacie.Model.SpecialiteDocteur;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class SpecialiteDocteurDTO
{
    Long idSpecialiteDocteur;
    String nomSpecialiteDocteur;



    public static SpecialiteDocteurDTO mapSpecialiteToDTO(SpecialiteDocteur specialiteDocteur)
    {
        return new SpecialiteDocteurDTO(
                specialiteDocteur.getIdSpecialiteDocteur(),
                specialiteDocteur.getNomSpecialiteDocteur()
        );

    }
    public static List<SpecialiteDocteurDTO> getSpecialitesDTO(List<SpecialiteDocteur> listSpecialites) {
        if (listSpecialites != null && !listSpecialites.isEmpty()) {
            return listSpecialites.stream()
                    .map(specialite -> new SpecialiteDocteurDTO(
                            specialite.getIdSpecialiteDocteur(),
                            specialite.getNomSpecialiteDocteur()))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList(); // La liste des spécialités est vide ou null
    }
}
