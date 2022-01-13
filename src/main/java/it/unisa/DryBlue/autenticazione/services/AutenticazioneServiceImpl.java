package it.unisa.DryBlue.autenticazione.services;

import it.unisa.DryBlue.autenticazione.dao.OperatoreDAO;
import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static it.unisa.DryBlue.autenticazione.domain.Ruolo.CLIENTE_ROLE;
import static it.unisa.DryBlue.autenticazione.domain.Ruolo.OPERATORE_ROLE;

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
    private final ClienteDAO clienteDAO;
    private final OperatoreDAO operatoreDAO;

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
            Cliente c;
            Operatore o;
            Utente u = new Utente();

            if ((clienteDAO.findByUsername(username)) != null) {
                c = clienteDAO.findByUsername(username);
                if (c.getPassword().equals(password)) {
                    u.setUsername(username);
                    u.setPassword(password);
                    u.setNome(c.getNome());
                    u.setCognome(c.getCognome());
                    u.setIndirizzo(c.getIndirizzo());
                    u.setCellulare(c.getNumeroTelefono());
                    u.setRuolo(new Ruolo(CLIENTE_ROLE));
                    return u;
                }
            }

            if ((operatoreDAO.findByUsername(username)) != null) {
                o = operatoreDAO.findByUsername(username);
                if (o.getPassword().equals(password)) {
                    u.setUsername(username);
                    u.setPassword(password);
                    u.setNome(o.getNome());
                    u.setCognome(o.getCognome());
                    u.setRuolo(new Ruolo(OPERATORE_ROLE));
                    return u;
                }
            }



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utente findByUsername(final String username) {
        return utenteDAO.findByUsername(username);
    }

}
