package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.PropostaModifica;
import org.springframework.data.repository.CrudRepository;

public interface PropostaModificaDAO extends CrudRepository<PropostaModifica, Integer> {
    PropostaModifica findByID(Integer id);
}
