package com.jseb23.NewPharmacie.Service;

import com.jseb23.NewPharmacie.Model.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class NotificationService
{
    JavaMailSender javaMailSender;
    public void envoyer(Validation validation)
    {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("no-reply@chillo.tech");
        message.setTo(validation.getUtilisateur().getMail()); // envoie au mail de l'utilisateur
        message.setSubject("Votre code d'activation"); // code activation

        String texte = String.format( // formatage message envoyé
                "Bonjour %s, <br /> Votre code d'action est %s; A bientôt",
                validation.getUtilisateur().getNomUtilisateur(),
                validation.getCode()
        );
        message.setText(texte);

        javaMailSender.send(message);
    }
}