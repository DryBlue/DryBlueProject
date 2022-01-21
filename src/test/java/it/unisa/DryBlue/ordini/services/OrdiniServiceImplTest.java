package it.unisa.DryBlue.ordini.services;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.dao.*;
import it.unisa.DryBlue.ordini.domain.*;
import it.unisa.DryBlue.servizi.domain.Servizio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrdiniServiceImplTest {

    private OrdiniService ordiniService;

    @Mock
    private OrdineDAO ordineDAO;
    @Mock
    private ClienteDAO clienteDAO;
    @Mock
    private PropostaModificaDAO propostaModificaDAO;
    @Mock
    private EtichettaDAO etichettaDAO;
    @Mock
    private SedeDAO sedeDAO;
    @Mock
    private RigaOrdineDAO rigaOrdineDAO;

    private Sede sede1, sede2;
    private Ordine ordine1;
    private PropostaModifica propostaModifica;
    private Etichetta etichetta;
    private Cliente cliente;
    private RigaOrdine rigaOrdine;
    private Servizio servizio;

    @BeforeEach
    public void init() {
        ordiniService = new OrdiniServiceImpl(ordineDAO, propostaModificaDAO, etichettaDAO, sedeDAO,
                clienteDAO, rigaOrdineDAO);
        final int y = 2022;
        final int m = 03;
        final int d = 02;
        LocalDate data1 = LocalDate.of(y, m, d);
        ordine1 = new Ordine(data1, "domicilio", "macchiato");
        final int x = 23;
        ordine1.setId(x);
        propostaModifica = new PropostaModifica("pronto");
        final double p = 20.0;
        servizio = new Servizio("nome", "tipo", "caratteristica", p);
        etichetta = new Etichetta();
        etichetta.setOrdine(ordine1);
        etichetta.setSede(sede1);
        rigaOrdine = new RigaOrdine(2);
        rigaOrdine.setOrdine(ordine1);
        rigaOrdine.setServizio(servizio);
        sede1 = new Sede("via verdi 12");
        sede2 = new Sede("via rossi 21");

    }

    @Test
    public void visualizzaSediTest() {
        List<Sede> list = new ArrayList<>();
        list.add(sede1);
        list.add(sede2);
        when(sedeDAO.findAll()).thenReturn(list);
        assertEquals(ordiniService.visualizzaSedi(), list);
    }

    @Test
    public void findById() {
        Integer ordine_id = ordine1.getId();
        Optional<Ordine> list = ordiniService.findById(ordine_id);
        when(ordineDAO.findById(ordine_id)).thenReturn(Optional.empty());
        assertEquals(ordiniService.findById(ordine_id), list);
    }

    @Test
    public void creaRigaOrdineTest() {
        when(rigaOrdineDAO.save(rigaOrdine)).thenReturn(rigaOrdine);
        ordiniService.creaRigaOrdine(rigaOrdine);
        verify(rigaOrdineDAO, times(1)).save(rigaOrdine);

    }

    @Test
    public void visualizzaOrdiniFiltroOperatoreTest() {
        List<Ordine> list1 = new ArrayList<>();
        List<Ordine> list2 = new ArrayList<>();
        when(ordineDAO.findAllByStato("Consegnato")).thenReturn(list1);
        assertEquals(ordiniService.visualizzaOrdiniFiltroOperatore("Consegnato"), list1);
        when(ordineDAO.findAllByStato("Macchiato")).thenReturn(list2);
        assertEquals(ordiniService.visualizzaOrdiniFiltroOperatore("Macchiato"), list2);
    }


    @Test
    public void visualizzaOrdiniFiltroUtenteTest() {
        List<Ordine> list1 = new ArrayList<>();
        Cliente c = new Cliente("user", "user", "Roccarainola, via Roma, 3", "Mario", "Rossi");
        c.setNumeroTelefono("2222222");
        ordine1.setCliente(c);
        list1.add(ordine1);

        List<Ordine> list2 = new ArrayList<>();
        list2.add(ordine1);

        when(ordineDAO.findAllByStato("Consegnato")).thenReturn(list1);
        assertEquals(ordiniService.visualizzaOrdiniFiltroUtente("Consegnato", "2222222"), list1);


        when(ordineDAO.findAllByStato("Macchiato")).thenReturn(list2);
        assertEquals(ordiniService.visualizzaOrdiniFiltroUtente("Macchiato", "2222222"), list2);
    }


}
