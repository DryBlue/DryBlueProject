package it.unisa.DryBlue.gestioneCliente.services;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;

import java.util.List;


public interface GestioneClienteService {
    Cliente aggiungiCliente(String numTelefono, String indirizzo, String nome, String cognome);

    Cliente reimpostaPassword(String password, Cliente cliente);

    void rimuoviCliente(Cliente cliente);

    List<Cliente> findTuttiIClienti();

    Cliente findByTelefono(String telefono);

}
