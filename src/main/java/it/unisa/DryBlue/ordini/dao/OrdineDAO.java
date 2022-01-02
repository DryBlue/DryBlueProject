package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.ordini.domain.Ordine;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrdineDAO extends CrudRepository<Ordine, Integer> {
    Ordine findByID(Integer id);
    List<Ordine> findAllByStato(String stato);
    List<Ordine> findAllByData(LocalDate date);
    List<Ordine> findAllByCliente(String username);
}
