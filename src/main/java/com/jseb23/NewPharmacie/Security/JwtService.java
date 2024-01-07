package com.jseb23.NewPharmacie.Security;

import com.jseb23.NewPharmacie.Service.UtilisateurService;
import com.jseb23.NewPharmacie.Model.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
@Slf4j
public class JwtService {
    private final String ENCRIPTION_KEY = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c"; // random generate key (site web)
    private UtilisateurService utilisateurService;

    /* ============ GENERE L'UTILISATEUR =============================*/
    public Map<String, String> generate(String username) // Map<String, String> Nom jeton et contenu du jeton
    {
        log.info(" Application dans JwtService -> generate");
        Utilisateur utilisateur = this.utilisateurService.loadUserByUsername(username);

        return this.generateJwt(utilisateur);
    }

    /* ================= EXTRAIT L'UTILISATEUR DU TOKEN ================*/
    public String extractUsername(String token) {
        log.info("Application dans JwtService -> extractUsername ");

        return this.getClaim(token, Claims::getSubject);// recupere l'username

    }
/*=================== VERIFICATION EXPIRATION TOKEN ==============================*/
    public boolean isTokenExpired(String token)
    {
        log.info("Application dans jwtService -> istokenexpired ");

        Date expirationDate = getExpirationDateFromToken(token);

        return expirationDate.before(new Date()); // si date inf a date token c'est ok
    }

    /* ================== EXTRAIT LA DATE EXPIRATION DANS TOKEN ======================*/
    private Date getExpirationDateFromToken(String token)
    {
        log.info("Application dans JwtService -> getExpirationDateFromToken");
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) // permet de recuperer les infos du claim
    {
        log.info("Application dans JwtService -> getClaim");
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) // recupere tous les claims du token
    {
        log.info("Application dans JwtService -> getAllClaims");
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey()) // extraire la signature
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
/* ========================= CREATION JETON =========================================*/
    private Map<String, String> generateJwt(Utilisateur utilisateur)
    {
        log.info("Application dans JwtService -> generateJwt");

        //fixe la date courante et la date expiration
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000; //  expiration 30 min après

        final Map<String, Object> claims = Map.of( // claims ( info ) de la personne
                "nom", utilisateur.getNomUtilisateur(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getUtilisateur()
        );

        final String bearer = Jwts.builder()// fixe le corps du bearer
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getUtilisateur())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    /* ===================== SIGNATURE DU TOKEN ====================*/
    private Key getKey()
    {
        log.info("Application dans JwtService -> getKey");

        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);

        return Keys.hmacShaKeyFor(decoder);
    }

}