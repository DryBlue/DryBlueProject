package it.unisa.DryBlue.autenticazione.dao;

import it.unisa.DryBlue.autenticazione.domain.Operatore;
import org.springframework.data.repository.CrudRepository;

public interface OperatoreDAO extends CrudRepository<Operatore,String> {

}
