package it.unisa.DryBlue.autenticazione.dao;

import it.unisa.DryBlue.autenticazione.domain.Utente;
import org.springframework.data.repository.CrudRepository;

public interface UtenteDAO extends CrudRepository<Utente, Integer> {
    Utente findByUsername(String username);
    // Utente login(String username, String password);
}
