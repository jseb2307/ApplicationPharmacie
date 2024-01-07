package com.jseb23.NewPharmacie.Security;

import com.jseb23.NewPharmacie.Service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Service
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private UtilisateurService utilisateurService;
    private JwtService jwtService;

    @Autowired
    public JwtFilter(UtilisateurService utilisateurService, JwtService jwtService) {
        this.utilisateurService = utilisateurService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        boolean isTokenExpired = true;

        log.info("Application dans JwtFilter");

        try {


            //extrait le token de l'en-tête Authorization de la requête HTTP. Selon la convention, le token doit être préfixé par Bearer
            final String authorization = request.getHeader("Authorization");// header de l'autorisation

            log.info("Header d'autorisation extrait : " + authorization);

            if (authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7); // recupere le token a partir de caractere 7
                isTokenExpired = jwtService.isTokenExpired(token);// vérification expiration token
                username = jwtService.extractUsername(token);
            }

            //Si le token n'est pas expiré, et que le nom d'utilisateur existe, et que la requête n'est pas encore authentifiée (c'est-à-dire que SecurityContextHolder.getContext().getAuthentication() == null est vrai), il va charger UserDetails par le nom d'utilisateur. Ensuite, il crée un nouvel objet
            if (!isTokenExpired && username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // si y a pas d'authentification (admin)
                UserDetails userDetails = (UserDetails) utilisateurService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

            //validation si le token n'est pas vide avant de l'utiliser avec jwtService.
            if (StringUtils.hasText(token)) {
                isTokenExpired = jwtService.isTokenExpired(token);
                username = jwtService.extractUsername(token);
            }
        } catch (Exception e) {
            log.error("Erreur lors du traitement du jeton JWT", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Erreur lors du traitement du jeton JWT");
            return;

        }

        //la requête au prochain filtre dans la chaîne de filtres Spring Security
        filterChain.doFilter(request, response);

    }
}