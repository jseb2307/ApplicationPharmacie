package com.jseb23.NewPharmacie.utilitaireTest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityTestUtils {

    public static String getBearerToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return "Bearer " + authentication.getCredentials().toString();
        }
        return null;
    }
}