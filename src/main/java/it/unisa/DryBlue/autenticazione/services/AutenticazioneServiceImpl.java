package it.unisa.DryBlue.autenticazione.services;

import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Utente;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *Implementa la classe che esplicita i metodi
 * definiti nell'interfaccia service per il
 * sottosistema Autenticazione.
 */
@Service
@RequiredArgsConstructor
public class AutenticazioneServiceImpl implements AutenticazioneService {

    /**
     *Si occupa delle operazioni CRUD per un utente.
     */
    private final UtenteDAO utenteDAO;

   
     /**
     * Implementa la funzionalità di login
     * per un Utente registrato.
     * @param username dell'utente.
     * @param password dell'utente.
     * @return un utente registrato.
     */
    @Override
    public Utente login(final String username, final String password) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] arr = md.digest(password.getBytes());
            Utente u;

            if ((u = utenteDAO.findByUsername(username)).getUsername().equals(password)) {
                return u;
            } 

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}