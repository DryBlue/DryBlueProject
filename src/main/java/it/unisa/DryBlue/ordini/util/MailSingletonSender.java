package it.unisa.DryBlue.ordini.util;

import it.unisa.DryBlue.ordini.domain.Ordine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSender;


/**
     * Classe singleton che permette di inviare una email informativa all'utente
     * quando si è verificato un cambiamento di stato ordine.
     *
     * @author Sabrina Ceccarelli, Miriam Ferrara
     */

    @Component
    @Scope("singleton")
    public class MailSingletonSender {

        @Autowired
        private JavaMailSender javaMailSender;

        /**
         * Metodo che permette di inviare una email
         *
         * @param object       Object che rappresenta l'oggetto coinvolto ai cambiamenti
         * @param destinatario String che rappresenta l'email del destinatario
         */
        public void sendEmail(final Object object, final String destinatario) {

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("drybluelaundry@gmail.com");
            msg.setTo(destinatario);

            msg.setSubject("DryBlueLaundry-COMUNICAZIONE DI SISTEMA-CAMBIO STATO ORDINE");
            String message = message(object);
            msg.setText(message);

            javaMailSender.send(msg);

        }


        /*
         * Metodo che ritorna la stringa contenente il messaggio informativo destinato
         * all'utente
         * @param obj Object che rappresenta l'oggetto coinvolto ai cambiamenti
         * @return String contenente il messaggio
         * */
        private String message(final Object obj) {
                Ordine ordine = (Ordine) obj;
                String stato = ordine.getStato();


                return "Salve,\nSiamo la lavanderia DryBlue.\n"
                       + "Questo messaggio è stato generato automaticamente dal sistema DryBlueLaundry "
                       + "per informarla che lo stato del suo ordine è stato cambiato in " + stato + ".\n"
                       + "Per maggiori informazioni acceda alla sua area utente e "
                       + "controlli la lista ordini attivi per tener traccia del suo ordine.\n\n"
                       + "Cordiali saluti.\n";

        }


}
