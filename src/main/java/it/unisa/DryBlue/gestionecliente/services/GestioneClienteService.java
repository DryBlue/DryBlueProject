package it.unisa.DryBlue.gestionecliente.services;

import it.unisa.DryBlue.gestionecliente.domain.Cliente;

public interface GestioneClienteService {
    Cliente aggiungiCliente(String numTelefono, String indirizzo, String nome, String cognome);

    Cliente reimpostaDati(String email, String password, String username);

    Boolean rimuoviCliente(String numTelefono);

    void aggiornaCliente(String numTelefono, String username, String password, String indirizzo, String nome, String cognome,
        String email);
}
