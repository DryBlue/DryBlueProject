package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.Sede;
import org.springframework.data.repository.CrudRepository;


public interface SedeDAO extends CrudRepository<Sede, Integer> {
    Sede findByIndirizzo(String indirizzo);
}
