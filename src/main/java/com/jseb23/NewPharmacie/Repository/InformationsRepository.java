package com.jseb23.NewPharmacie.Repository;

import com.jseb23.NewPharmacie.Model.Informations;
import com.jseb23.NewPharmacie.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationsRepository extends JpaRepository<Informations,Long> {


}