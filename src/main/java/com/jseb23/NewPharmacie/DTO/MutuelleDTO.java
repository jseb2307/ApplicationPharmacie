package com.jseb23.NewPharmacie.DTO;

import com.jseb23.NewPharmacie.Model.Docteur;
import com.jseb23.NewPharmacie.Model.Mutuelle;
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
public class MutuelleDTO
{
    Long idMutuelle;
    String nomMutuelle;
    InformationsDTO informations;
    public static MutuelleDTO mapMutuelleToDTO(Mutuelle mutuelle)
    {
        InformationsDTO informationsDTO = mutuelle.getInformations() != null ?
                InformationsDTO.mapInformationsToDTO(mutuelle.getInformations()) :
                null;

        return new MutuelleDTO(
                mutuelle.getIdMutuelle(),
                mutuelle.getNomMutuelle(),
                informationsDTO
        );
    }

    public static List<MutuelleDTO> getMutuellesDTO(List<Mutuelle> listMutuelles) {
        if (listMutuelles != null && !listMutuelles.isEmpty()) {
            return listMutuelles.stream()
                    .map(mutuelle-> new MutuelleDTO(
                            mutuelle.getIdMutuelle(),
                            mutuelle.getNomMutuelle(),
                            InformationsDTO.mapInformationsToDTO(mutuelle.getInformations())
                    ))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList(); // La liste des mutuelles est vide ou null
    }


}
