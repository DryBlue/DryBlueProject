package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.ordini.domain.RigaOrdine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RigaOrdineDAO extends CrudRepository<RigaOrdine, Integer> {
    //List<RigaOrdine> findAllByOrdine(Ordine ordine);
}
