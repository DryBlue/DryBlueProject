package it.unisa.DryBlue.autenticazione.controller;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class AutenticazioneController {

    @Autowired
    private ClienteDAO clienteDAO;

    private Cliente cliente;



}
