package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.PropostaModifica;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PropostaModificaDAO extends CrudRepository<PropostaModifica, Integer> {
    Optional<PropostaModifica> findById(Integer id);
}
