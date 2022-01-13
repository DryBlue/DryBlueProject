package it.unisa.DryBlue.autenticazione.services;

import it.unisa.DryBlue.autenticazione.domain.Utente;

/**
 * Interfaccia per i metodi del sottosistema Autenticazione.
 */
public interface AutenticazioneService {
     /**
      * Firma del metodo che implementa la funzione di login.
      * @param username dell'utente da loggare.
      * @param password dell'utente da loggare.
      * @return dell'utente da loggato.
      */
     Utente login(String username, String password);

     /**
      * Implementa la funzionalità di trovare un utente.
      * @param username La username dell'utente
      * @return L'utente se presente, altrimenti null
      */
     Utente findByUsername(String username);
}
