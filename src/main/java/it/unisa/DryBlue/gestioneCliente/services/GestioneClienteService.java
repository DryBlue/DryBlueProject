package it.unisa.DryBlue.gestioneCliente.services;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;

import java.util.List;


public interface GestioneClienteService {

    List<Cliente> findTuttiIClienti();

    Cliente findByTelefono(String telefono);

}
