package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.Etichetta;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EtichettaDAO extends CrudRepository<Etichetta, Integer> {
    Optional<Etichetta> findById(Integer id);
}
