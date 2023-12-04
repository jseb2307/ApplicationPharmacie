package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Patient;
import com.jseb23.NewPharmacie.Repository.GenericRepository;
import com.jseb23.NewPharmacie.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService extends GenericService<Patient, Long> {


    private PatientRepository patientRepository;

    @Autowired
    public PatientService(GenericRepository<Patient, Long> repository) {
        super(repository);
        this.patientRepository = patientRepository;
    }

    /**
     * Trouve tous les patients dont le nom contient la chaîne spécifiée.
     *
     * @param nom La chaîne à rechercher.
     * @return La liste des patients trouvés.
     */
    public List<Patient> findByNomContaining(String nom) {
        return patientRepository.findAll(patient -> patient.getNom().contains(nom));
    }

    /**
     * Trouve tous les patients dont la date de naissance est comprise entre les deux dates spécifiées.
     *
     * @param dateDebut La date de début.
     * @param dateFin La date de fin.
     * @return La liste des patients trouvés.
     */
    public List<Patient> findByDateNaissanceBetween(LocalDate dateDebut, LocalDate dateFin) {
        return repository.findAll(patient -> patient.getDateNaissance().isAfter(dateDebut) && patient.getDateNaissance().isBefore(dateFin));
    }

    /**
     * Trouve tous les patients dont le sexe est le sexe spécifié.
     *
     * @param sexe Le sexe à rechercher.
     * @return La liste des patients trouvés.
     */
    public List<Patient> findBySexe(Sexe sexe) {
        return repository.findAll(patient -> patient.getSexe() == sexe);
    }
}
