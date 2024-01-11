package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Validation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
@AllArgsConstructor
@Service
public class NotificationService {

    JavaMailSender javaMailSender;

    public void envoyer(Validation validation) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // lien de redirectection
            String lienActivation = "http://localhost:63342/ApplicationPharmacie/styles/maPharmacie.html#pageActivation?utilisateur=" + validation.getUtilisateur().getUtilisateur() + "&code=" + validation.getCode();


            // Adresse e-mail expéditeur
            helper.setFrom("no-reply@appliPharma");

            // Adresse e-mail destinataire (utilisateur)
            helper.setTo(validation.getUtilisateur().getEmail());

            // Sujet du message
            helper.setSubject("Activation de votre compte");

            // Construction du message HTML
            String texte = String.format(
                    "<p>Bonjour %s %s,</p>" +
                            "<p>Merci de vous être inscrit. Pour activer votre compte, veuillez cliquer sur le lien ci-dessous :</p>" +
                                validation.getCode()+
                            "<p>Ce code  est valable pendant 10 minutes.</p>" +
                            "<a href='" + lienActivation + "'>cliquer ici</a> pour activer votre compte." +
                            "<p>A bientôt!</p>",
                    validation.getUtilisateur().getPrenomUtilisateur(),
                    validation.getUtilisateur().getNomUtilisateur(),
                    validation.getUtilisateur().getUtilisateur(),
                    validation.getCode(),
                    lienActivation
            );

            helper.setText(texte, true);

            log.info("");

            javaMailSender.send(message);

            // Log réussite
            log.info("E-mail d'activation envoyé avec succès à {}", validation.getUtilisateur().getEmail());

        } catch (MessagingException e) {
            // Log erreur
            log.error("Erreur lors de l'envoi de l'e-mail d'activation", e);
            // Propagation de l'exception
            throw e;
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
