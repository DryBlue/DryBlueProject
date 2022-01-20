package it.unisa.DryBlue.gestioneCliente.services;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;

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
public class GestioneClienteServiceImplTest {

    private GestioneClienteService gestioneClienteService;

    @Mock
    private ClienteDAO clienteDAO;

    private Cliente cliente1, cliente2;

    @BeforeEach
    public void init() {
        gestioneClienteService = new GestioneClienteServiceImpl(clienteDAO);
        cliente1 = new Cliente("user", "user", "Roccarainola, via Roma, 3", "Mario", "Rossi");
        cliente2 = new Cliente("user2", "user2", "Nola, P.zza Duomo, 3", "Felice", "Russo");
        cliente1.setNumeroTelefono("11122334444");
        cliente2.setNumeroTelefono("3332233222");
    }

    @Test
    public void findTuttiIClientiTest() {
        List<Cliente> list = new ArrayList<>();
        list.add(cliente1);
        when(clienteDAO.findAll()).thenReturn(list);
        assertEquals(gestioneClienteService.findTuttiIClienti(), list);
    }

    @Test
    public void findByTelefonoTest() {
        String telefono = "11122334444";
        when(clienteDAO.findByNumeroTelefono(telefono)).thenReturn(cliente1);
        assertEquals(gestioneClienteService.findByTelefono(telefono), cliente1);
    }


}
