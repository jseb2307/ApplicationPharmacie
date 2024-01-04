package com.jseb23.NewPharmacie.Repository;


import com.jseb23.NewPharmacie.Model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {

    Optional<Validation> findByCode(String code);
}