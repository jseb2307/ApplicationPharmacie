package com.jseb23.NewPharmacie.Repository;

import com.jseb23.NewPharmacie.Model.Role;
import com.jseb23.NewPharmacie.TypeDeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByLibelle(TypeDeRole typeDeRole);
}