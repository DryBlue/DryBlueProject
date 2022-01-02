package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.Etichetta;
import org.springframework.data.repository.CrudRepository;

public interface EtichettaDAO extends CrudRepository<Etichetta, Integer> {
    Etichetta findByID(Integer id);
}
