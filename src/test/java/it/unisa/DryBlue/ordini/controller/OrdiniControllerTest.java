package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.domain.*;
import it.unisa.DryBlue.ordini.services.OrdiniService;
//import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;
import it.unisa.DryBlue.servizi.services.ServizioService;
//import it.unisa.DryBlue.servizi.services.ServizioServiceImpl;
//import org.apache.catalina.LifecycleState;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdiniControllerTest {

    /*@MockBean
    private OrdiniService ordiniService;
    @MockBean
    private ServizioService servizioService;


    @Autowired
    private MockMvc mockMvc;

    private Ordine ordine;
    private Sede sede;
    private RigaOrdine rigaOrdine;
    private PropostaModifica propostaModifica;
    private Cliente cliente;
    private Utente u;
    private Ruolo ruolo;
    private Operatore operatore;
    private Servizio servizio;
    private Set<RigaOrdine> righe;
    private Utente ucliente;

    @BeforeEach
    public void init() {
        ucliente = new Utente("user", "user");
        ucliente.setCellulare("222333666");
        ruolo = new Ruolo();
        ruolo.setName(ruolo.CLIENTE_ROLE);
        ucliente.setRuolo(ruolo);

        cliente = new Cliente("user", "user", "via verdi ", "Marco", "rossi");
        cliente.setNumeroTelefono("222333666");


        u = new Utente("admin", "admin");
        ruolo = new Ruolo();
        ruolo.setName(ruolo.OPERATORE_ROLE);
        u.setRuolo(ruolo);

        operatore = new Operatore("admin", "Marco", "Polo");
        operatore.setUsername("mpolo");

        rigaOrdine = new RigaOrdine(2);
        final double prezzo = 10.50;
        servizio = new Servizio("Lavaggio", "secco", "veloce", prezzo);
        final int x = 3;
        servizio.setId(x);

        sede = new Sede();
        sede.setIndirizzo("Ariano Irpino, via Cardito, 52");
        final int y = 2022;
        final int m = 03;
        final int d = 02;
        LocalDate data1 = LocalDate.of(y, m, d);
        ordine = new Ordine(data1, "domicilio", "macchiato");
        ordine.setSede(sede);
        ordine.setNote("");
        ordine.setCliente(cliente);

    }

    @Test
    public void listaOrdiniTest() throws Exception{

        List <Ordine> list= new ArrayList<>();
        list.add(ordine);
        when(ordiniService.visualizzaOrdiniTotali()).thenReturn(list);
        this.mockMvc.perform(post("/ListaOrdini")
                .param("filter","Totali")
                .sessionAttr("utente",u))
                .andExpect(model().attribute("ordini",list))
                .andExpect(view().name("ordini/ListaOrdini"));

       when(ordiniService.visualizzaOrdiniFiltroOperatore("Attivi")).thenReturn(list);
        this.mockMvc.perform(post("/ListaOrdini")
                        .param("filter","Attivi")
                        .sessionAttr("utente",u))
                .andExpect(model().attribute("ordini",list))
                .andExpect(view().name("ordini/ListaOrdini"));

        when(ordiniService.visualizzaOrdiniFiltroUtente("Attivi",ucliente.getCellulare())).thenReturn(list);
        this.mockMvc.perform(post("/ListaOrdini")
                        .param("filter","Attivi")
                        .sessionAttr("utente",ucliente))
                .andExpect(model().attribute("ordini",ucliente.getCellulare()))
                .andExpect(view().name("ordini/ListaOrdini"));

  }

    @Test
    public void modificaStatoTest(){
        final int y = 2022;
        final int m = 03;
        final int d = 02;
        Integer id_ordine = ordine.getId();
        LocalDate data = LocalDate.of(y, m, d);
        List <Ordine> list= new ArrayList<>();
        list.add(ordine);
        when(ordiniService.modificaOrdine(data)).thenReturn(true);
        this.mockMvc.perform(post("/ListaOrdini/ModificaOrdine")
                .param("stato","macchiato")
                .param("idOrdine",id_ordine)
                .sessionAttr("utente",u))
                .andExpect(view().name("listaOrdini");

    }

    @Test
    public void modificaOrdine(){
        List <Ordine> list= new ArrayList<>();
        list.add(ordine);
        Integer id_ordine = ordine.getId();
        when(ordiniService.findById(id_ordine)).thenReturn();
    }*/



}



