package com.jseb23.NewPharmacie.DTO;

import com.jseb23.NewPharmacie.Model.Mutuelle;
import com.jseb23.NewPharmacie.Model.Patient;
import com.jseb23.NewPharmacie.Model.Docteur;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.jseb23.NewPharmacie.DTO.DocteurDTO.getDocteursDTO;

@Data
@AllArgsConstructor
public class PatientDTO
{
        Long idPatient;
        String nomPatient;
        String prenomPatient;
        LocalDate dateDeNaissance;
        String numeroSecuPatient;

    // Informations du patient
    InformationsDTO informations;

   List<DocteurDTO> listDocteurs;

    // Nom de la mutuelle
    String nomMutuelle;
    Long idMutuelle;


/*======================== LISTE NOM ET PRENOM MEDECINS PATIENT===================*/
    private static List<String> getNomsPrenomsDocteurs(List<Docteur> listDocteurs) {
        if (listDocteurs != null && !listDocteurs.isEmpty()) {
            return listDocteurs.stream()
                    .map(docteur -> docteur.getNomDocteur() + " " + docteur.getPrenomDocteur())
                    .collect(Collectors.toList());
        }

        return Collections.emptyList(); /*La liste des docteurs est vide ou nul*/
    }

    public static PatientDTO mapPatientToDTO(Patient patient) {
        return new PatientDTO(
                patient.getIdPatient(),
                patient.getNomPatient(),
                patient.getPrenomPatient(),
                patient.getDateDeNaissance(),
                patient.getNumeroSecuPatient(),
                InformationsDTO.mapInformationsToDTO(patient.getInformations()),
                getDocteursDTO(patient.getListDocteurs()),
                patient.getMutuelle() != null ? patient.getMutuelle().getNomMutuelle() : null,
                patient.getMutuelle() != null ? patient.getMutuelle().getIdMutuelle() : null
        );
    }


    public static List<PatientDTO> mapPatientListToDTOList(List<Patient> patientList) {
        return patientList.stream()
                .map(PatientDTO::mapPatientToDTO)
                .collect(Collectors.toList());
    }

    public static Patient mapDTOToPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setNomPatient(patientDTO.getNomPatient());
        patient.setPrenomPatient(patientDTO.getPrenomPatient());
        patient.setDateDeNaissance(patientDTO.getDateDeNaissance());
        patient.setNumeroSecuPatient(patientDTO.getNumeroSecuPatient());
        /* Informations du patient*/
        patient.setInformations(InformationsDTO.mapDTOToInformations(patientDTO.getInformations()));

        return patient;
    }
}

