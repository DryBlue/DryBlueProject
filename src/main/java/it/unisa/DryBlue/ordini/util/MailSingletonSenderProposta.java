package it.unisa.DryBlue.ordini.util;

import it.unisa.DryBlue.ordini.domain.Ordine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


/**
     * Classe singleton che permette di inviare una email informativa all'utente
     * quando si è verificato un cambiamento di stato ordine.
     *
     * @author Sabrina Ceccarelli, Miriam Ferrara
     */

    @Component
    @Scope("singleton")
    public class MailSingletonSenderProposta {

        @Autowired
        private JavaMailSender javaMailSender;

        public void sendEmail(String destinatario, String scelta) {

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("drybluelaundry@gmail.com");
            msg.setTo(destinatario);

            msg.setSubject("DryBlueLaundry-COMUNICAZIONE DI SISTEMA-VALUTAZIONE PROPOSTA");
            String message = message(scelta);
            msg.setText(message);

            javaMailSender.send(msg);

        }

    private String message(String scelta) {

        return "Salve,\nSiamo la lavanderia DryBlue.\n" +
                "Questo messaggio è stato generato automaticamente dal sistema DryBlueLaundry " +
                "per informarla che la Proposta del cambio data da lei richiesta è stata " + scelta + ".\n" +
                "Per maggiori informazioni acceda alla sua area utente e " +
                "controlli la lista ordini attivi per tener traccia del suo ordine.\n\n" +
                "Cordiali saluti.\n";
           }
}
