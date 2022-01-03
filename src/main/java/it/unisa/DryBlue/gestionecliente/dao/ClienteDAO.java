package it.unisa.DryBlue.gestionecliente.dao;

import it.unisa.DryBlue.gestionecliente.domain.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteDAO extends CrudRepository<Cliente, String> {
    Cliente findByNumeroTelefono(String numTel);
    Cliente findByNome(String nome);
    Cliente findByCognome(String cognome);
}
