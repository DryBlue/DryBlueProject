package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.Etichetta;
import it.unisa.DryBlue.ordini.domain.Ordine;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EtichettaDAO extends CrudRepository<Etichetta, Integer> {
    Etichetta findByOrdine(Ordine ordine);
}
