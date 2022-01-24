package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import it.unisa.DryBlue.ordini.domain.*;
import it.unisa.DryBlue.ordini.services.OrdiniService;
import it.unisa.DryBlue.servizi.domain.Servizio;
import it.unisa.DryBlue.servizi.services.ServizioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import java.util.*;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdiniControllerTest {

    @MockBean
    private OrdiniService ordiniService;
    @MockBean
    private ServizioService servizioService;


    @MockBean
    private GestioneClienteService gestioneClienteService;

    @Autowired
    private MockMvc mockMvc;

    private Ordine ordine;
    private Sede sede;
    private Set<RigaOrdine> rigaOrdine;
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
        ucliente.setId(ucliente.getId());

        cliente = new Cliente("user", "user", "via verdi ", "Marco", "rossi");
        cliente.setNumeroTelefono("222333666");
        cliente.setEmail("marco@gmail.com");


        u = new Utente("admin", "admin");
        ruolo = new Ruolo();
        ruolo.setName(ruolo.OPERATORE_ROLE);
        u.setRuolo(ruolo);
        final int newId = 5;
        u.setId(newId);

        operatore = new Operatore("admin", "Marco", "Polo");
        operatore.setUsername("admin");


        rigaOrdine = new HashSet<>();
        final double prezzo = 10.50;
        servizio = new Servizio("Lavaggio", "secco", "veloce", prezzo);
        final int x = 3;
        servizio.setId(x);

        final int z = 6;
        sede = new Sede("Ariano Irpino, via Cardito, 52");
        sede.setId(z);


        final int y = 2022;
        final int m = 03;
        final int d = 02;
        final int idNew = 3;
        LocalDate data1 = LocalDate.of(y, m, d);
        ordine = new Ordine(data1, "domicilio", "Pronto");
        ordine.setSede(sede);
        ordine.setNote("blue");
        ordine.setCliente(cliente);
        ordine.setId(idNew);
        ordine.getCliente().setEmail(cliente.getEmail());
        ordine.getCliente().setNumeroTelefono(cliente.getNumeroTelefono());
        ordine.setRigheOrdine(rigaOrdine);


    }

    @Test
    public void listaOrdiniTest() throws Exception {
//sessione cliente e operatore
        List<Ordine> list = new ArrayList<>();
        list.add(ordine);
        when(ordiniService.visualizzaOrdiniTotali()).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                .param("filter", "Totali")
                .sessionAttr("utente", u))
                .andExpect(model().attribute("ordini", list))
                .andExpect(view().name("ordini/ListaOrdini"));

       when(ordiniService.visualizzaOrdiniFiltroOperatore("Attivi")).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter", "Attivi")
                        .sessionAttr("utente", u))
                .andExpect(model().attribute("ordini", list))
                .andExpect(view().name("ordini/ListaOrdini"));

        when(ordiniService.visualizzaOrdiniFiltroOperatore("ProvaNull")).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter", "ProvaNull")
                        .sessionAttr("utente", u))
                .andExpect(model().attribute("ordini", list))
                .andExpect(view().name("ordini/ListaOrdini"));
        // aggiungi
        when(ordiniService.visualizzaOrdiniFiltroUtente("Attivi", ucliente.getCellulare())).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter", "Attivi")
                        .sessionAttr("utente", ucliente))
                .andExpect(model().attribute("ordini", list))
               .andExpect(view().name("ordini/ListaOrdini"));


        when(ordiniService.visualizzaOrdiniFiltroUtente("Totali", ucliente.getCellulare())).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter", "Totali")
                        .sessionAttr("utente", ucliente))
                .andExpect(model().attribute("ordini", list))
                .andExpect(view().name("ordini/ListaOrdini"));


  }

    @Test
    public void modificaStatoTest() throws Exception {
        List<Ordine> list = new ArrayList<>();
        list.add(ordine);
       // ordiniService.creazioneOrdine(rigaOrdine, "Marco", "domicilio",  sede.getIndirizzo(),  LocalDate.of(2022, 03, 02), "blue");

       when(ordiniService.findById(ordine.getId())).thenReturn(ordine);
        when(ordiniService.modificaOrdine(null, null, "Pronto", ordine.getId())).thenReturn(true);
        this.mockMvc.perform(post("/ordini/ListaOrdini/ModificaOrdine")
                .param("stato", "Pronto")
                .param("idOrdine", String.valueOf(ordine.getId()))
                .sessionAttr("utente", u))
                 .andExpect(view().name("ordini/ListaOrdini"));
        System.out.println(" id " + ordine.getId() + " email " + ordine.getCliente().getEmail());


    }

    @Test
    public void modificaStatoFail() throws Exception {
        Cliente c = new Cliente();
        ordine.setCliente(c);
        when(ordiniService.findById(ordine.getId())).thenReturn(ordine);
        when(ordiniService.modificaOrdine(null, null, "Pronto", ordine.getId())).thenReturn(true);
        this.mockMvc.perform(post("/ordini/ListaOrdini/ModificaOrdine")
                        .param("stato", "Pronto")
                        .param("idOrdine", String.valueOf(ordine.getId()))
                        .sessionAttr("utente", u))
                .andExpect(view().name("ordini/ListaOrdini"));
    }

    @Test
    public void modificaOrdine() throws Exception {
        List<Ordine> list = new ArrayList<>();
        list.add(ordine);
        Sede sede2 = new Sede("Ariano Irpino, AV, via Cardito, 52");
        final int idA = 6;
        sede2.setId(idA);
         ordine.getSede().setIndirizzo(sede2.getIndirizzo());
        System.out.println("id " + ordine.getId() + "sede2 " + sede2 + "\nsede "  + sede);

        when(ordiniService.findById(ordine.getId())).thenReturn(ordine);
        when(ordiniService.findByIndirizzo(ordine.getSede().getIndirizzo())).thenReturn(sede);
        when(ordiniService.modificaOrdine(null, sede2, null, ordine.getId())).thenReturn(true);

        this.mockMvc.perform(post("/ListaOrdini/ModificaOrdine")
                .param("idOrdine", ordine.getId().toString())
                .sessionAttr("utente", u));
              //  .andExpect(view().name("/ordini/ListaOrdini" + ordiniService.visualizzaOrdiniTotali() ));
    }

/*

    @Test
    public void ValutazioneAccetta() throws Exception{
        List <Ordine> list= new ArrayList<>();
        list.add(ordine);
        when(ordiniService.findById(ordine.getId())).thenReturn(ordine);
        this.mockMvc.perform(post("/ordini/ValutazioneAccetta")
                        .param("accetta", ordine.getId().toString())
                        .sessionAttr("utente", u));
               // .andExpect(view().name("ordini/ListaOrdini"));

    }*/

    @Test
    public void ValutazioneRifiuta() throws Exception {

    }

    @Test
    public void aggiuntaOrdineTest() throws Exception {
        Set<RigaOrdine> righe1 = new HashSet<>();
        final int quantity = 3;
        RigaOrdine riga = new RigaOrdine(quantity);
        righe1.add(riga);

        List<Servizio> lists = new ArrayList<>();
        lists.add(servizio);
        List<Sede> list = new ArrayList<>();
        list.add(sede);
        List<Cliente> listc = new ArrayList<>();
        listc.add(cliente);

        when(servizioService.findServizioById(servizio.getId())).thenReturn(servizio);
        when(servizioService.findServizi()).thenReturn(lists);
        when(ordiniService.visualizzaSedi()).thenReturn(list);
        when(gestioneClienteService.findTuttiIClienti()).thenReturn(listc);
        //when(ordiniService.creaRigaOrdine(riga)).thenReturn(void);
        this.mockMvc.perform(post("/ordini/aggiuntaRiga")
                        .param("idServizio", String.valueOf(servizio.getId()))
                        .param("quantity", "3")
                        .sessionAttr("utente", u))
                .andExpect(model().attribute("righe", righe1))
                .andExpect(model().attribute("servizi", lists))
                .andExpect(model().attribute("sedi", list))
                .andExpect(model().attribute("clienti", listc))
                .andExpect(view().name("ordini/aggiuntaOrdine"));

        final int year = 2022;
        final int month = 03;
        LocalDate data = LocalDate.of(year, month, 02);
        Ordine o = new Ordine();
        when(ordiniService.creazioneOrdine(righe1, cliente.getUsername(), "domicilio", sede.getIndirizzo(), data, "prova")).thenReturn(o);

        this.mockMvc.perform(post("/ordini/aggiuntaOrdine")
                        .param("cliente", "user")
                        .param("ritiro", "domicilio")
                        .param("sedeDesiderata", "Ariano Irpino, via Cardito, 52")
                        .param("date", String.valueOf(data))
                        .param("note", "prova")
                        .sessionAttr("utente", u))
                .andExpect(view().name("/LoggedHomepage"));

    }

    @Test
    public void aggiuntaOrdineTestFail() throws Exception {

        final int year = 2022;
        final int month = 03;
        LocalDate data = LocalDate.of(year, month, 02);

        this.mockMvc.perform(post("/ordini/aggiuntaOrdine")
                        .param("cliente", "user")
                        .param("ritiro", "domicilio")
                        .param("sedeDesiderata", "Ariano Irpino, via Cardito, 52")
                        .param("date", String.valueOf(data))
                        .param("note", "prova")
                        .sessionAttr("utente", u))
                .andExpect(view().name("error/500"));

    }
}
