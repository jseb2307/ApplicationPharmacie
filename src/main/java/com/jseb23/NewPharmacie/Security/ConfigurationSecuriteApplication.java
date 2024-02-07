package com.jseb23.NewPharmacie.Security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@Slf4j
public class ConfigurationSecuriteApplication{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;


    public ConfigurationSecuriteApplication(BCryptPasswordEncoder bCryptPasswordEncoder, JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    /* ======================== FILTRES DE CONNEXION ===========================================================*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("dans ConfigurationSecuriteApplication -> securityFilterChain");
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                .authorizeHttpRequests(authorize ->
                                authorize
                                        .requestMatchers(new AntPathRequestMatcher("/inscription", "POST")).permitAll()// accepte l'inscription sans authentification
                                        .requestMatchers(new AntPathRequestMatcher("/activation", "POST")).permitAll()
                                         .requestMatchers(new AntPathRequestMatcher("/connexion", "POST")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/informations/create", "POST")).permitAll()
                                        .requestMatchers(new AntPathRequestMatcher("/connexion", HttpMethod.OPTIONS.toString())).permitAll()// pré requête
                                        .requestMatchers(new AntPathRequestMatcher("/activation", HttpMethod.OPTIONS.toString())).permitAll()
                                        .anyRequest().authenticated()) // les autres requetes doivent être identifiées
                                        .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                             httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set session policy to stateless
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)//
                .build();
    }

/*   @Configuration
    @Profile("test")
    public class TestSecurityConfiguration {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .cors(Customizer.withDefaults())
                    .csrf(AbstractHttpConfigurer::disable) // Disable CSRF
                    .authorizeHttpRequests(authorize ->
                            authorize
                                    .anyRequest().permitAll())
                   .build();
        }
    }*/

    /* ========= AUTHENTIFICATION DE L'UTILISATEUR ===================================================================*/
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        log.info("dans authenticationManager");
        return authenticationConfiguration.getAuthenticationManager();

    }

/* ============================ AUTHENTIFICATION S'APPUYANT SUR LA BASE ================================================*/
    @Bean
    public AuthenticationProvider authenticationProvider (UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        log.info("dans authenticationProvider");
        return daoAuthenticationProvider;
    }


}
