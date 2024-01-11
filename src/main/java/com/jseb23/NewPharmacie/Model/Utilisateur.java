package com.jseb23.NewPharmacie.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utilisateur implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUtilisateur;

    @NotNull
    @NotBlank
    @NotEmpty
    String nomUtilisateur;

    @NotNull
    @NotBlank
    @NotEmpty
    String prenomUtilisateur;

    @NotNull
    @NotBlank
    @NotEmpty
    String utilisateur;

    @NotNull
    @NotBlank
    @NotEmpty
    String motDePasseUtilisateur;


    String motDePasseProvisoire;

    @NotNull
    @NotBlank
    @NotEmpty
    String email;

    boolean actif = false;

    /*========================= MAPPING ==========================*/


    @ManyToOne
    @JoinColumn(name = "idInformations", referencedColumnName = "idInformations")
    Informations informations;

    @ManyToOne
    @JoinColumn(name = "IdRole")
    Role role;

    @OneToMany(mappedBy = "utilisateur")
    Collection<Validation> validations;
    /*============================ METHODS ================================*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE" + this.role.getLibelle()));
    }

    @Override
    public String getPassword() {
        return this.getMotDePasseUtilisateur();
    }

    @Override
    public String getUsername() {
        return this.utilisateur;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.actif;
    }

    @Override
    public boolean isEnabled() {
        return this.actif;
    }
}
