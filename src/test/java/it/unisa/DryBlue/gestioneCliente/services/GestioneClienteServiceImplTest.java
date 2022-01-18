package it.unisa.DryBlue.gestioneCliente.services;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GestioneClienteServiceImplTest {

    @Autowired
    private GestioneClienteService gestioneClienteService;

    private Cliente cliente1, cliente2;

    @BeforeEach
    public void init() {
        cliente1 = new Cliente("user", "user", "Roccarainola, via Roma, 3", "Mario", "Rossi");
        cliente2 = new Cliente("user2", "user2", "Nola, P.zza Duomo, 3", "Felice", "Russo");
        cliente1.setNumeroTelefono("11122334444");
        cliente2.setNumeroTelefono("3332233222");
    }

    @Test
    public void findTuttiIClientiTest() {
        List<Cliente> list = new ArrayList<>();
        list.add(cliente1);
        list.add(cliente2);
        assertEquals(gestioneClienteService.findTuttiIClienti(), list);
    }

    @Test
    public void findByTelefonoTest(){
        String telefono = "3332233222";
        assertEquals(gestioneClienteService.findByTelefono(telefono), cliente2);
    }
}
