package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.Sede;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SedeDAO extends CrudRepository<Sede, Integer> {
}
