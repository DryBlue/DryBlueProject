package it.unisa.DryBlue.gestioneCliente.controller;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GestioneClienteControllerIT {


    private GestioneClienteService gestioneClienteService;
    @Mock
    private ClienteDAO gestioneClienteDAO;
    private Cliente cliente, cliente1;

    @BeforeEach
    public void init() {
        gestioneClienteService = new GestioneClienteServiceImpl(gestioneClienteDAO);
        final String telefono = "3934447771";

        cliente = new Cliente("userA", "userA", "via Verdi 13", "Fabio", "Rossi");
        cliente.setNumeroTelefono(telefono);
        cliente.setEmail("m@gmail.com");

        cliente1 = new Cliente("userB", "userB", "via Rossi 45", "Francesca", "Verdi");
        cliente1.setNumeroTelefono("395566898");
        cliente1.setEmail("");

    }

    @Test
    public void trovaTuttiIClientiTest() {
        List<Cliente> list = new ArrayList<>();
        list.add(cliente);
        list.add(cliente1);
        when(gestioneClienteDAO.findAll()).thenReturn(list);
        assertEquals(gestioneClienteService.findTuttiIClienti(), list);
    }

    @Test
    public void dettagliClienteTest() {
        String telefono = cliente.getNumeroTelefono();
        when(gestioneClienteDAO.findByNumeroTelefono(telefono)).thenReturn(cliente);
        assertEquals(gestioneClienteService.findByTelefono(telefono), cliente);
    }
}
