package it.unisa.DryBlue.servizi.dao;

import it.unisa.DryBlue.servizi.domain.Servizio;
import org.springframework.data.repository.CrudRepository;

public interface ServizioDAO extends CrudRepository<Servizio, Integer> {
}
