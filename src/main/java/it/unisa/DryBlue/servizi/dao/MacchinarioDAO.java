package it.unisa.DryBlue.servizi.dao;

import it.unisa.DryBlue.servizi.domain.Macchinario;
import org.springframework.data.repository.CrudRepository;

public interface MacchinarioDAO extends CrudRepository<Macchinario, String> {
}
