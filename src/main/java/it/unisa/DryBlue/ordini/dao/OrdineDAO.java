package it.unisa.DryBlue.ordini.dao;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.domain.Ordine;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrdineDAO extends CrudRepository<Ordine, Integer> {
    List<Ordine> findAllByStato(String stato);
    List<Ordine> findAllByDataConsegnaDesiderata(LocalDate date);
    List<Ordine> findAllByCliente(Cliente cliente);
}
