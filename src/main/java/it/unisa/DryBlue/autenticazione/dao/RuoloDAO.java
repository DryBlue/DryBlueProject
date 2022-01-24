package it.unisa.DryBlue.autenticazione.dao;

import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import org.springframework.data.repository.CrudRepository;

public interface RuoloDAO extends CrudRepository<Ruolo, Integer> {
}
