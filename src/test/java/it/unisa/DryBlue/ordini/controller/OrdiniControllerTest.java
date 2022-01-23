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
    private Sede sede, sede2;
    private Set<RigaOrdine> rigaOrdine;
    private PropostaModifica propostaModifica;
    private Cliente cliente;
    private Utente u, ucliente;
    private Ruolo ruolo;
    private Operatore operatore;
    private Servizio servizio;



    @BeforeEach
    public void init() {

        cliente = new Cliente("user", "user", "via verdi ", "Marco", "rossi");
        cliente.setNumeroTelefono("222333666");
        cliente.setEmail("marco@gmail.com");


        ucliente = new Utente("user", "user");
        ucliente.setCellulare(cliente.getNumeroTelefono());
        ruolo = new Ruolo();
        ruolo.setName(ruolo.CLIENTE_ROLE);
        ucliente.setRuolo(ruolo);
        ucliente.setId(ucliente.getId());


        u = new Utente("admin", "admin");
        ruolo = new Ruolo();
        ruolo.setName(ruolo.OPERATORE_ROLE);
        u.setRuolo(ruolo);
        u.setId(5);


        operatore = new Operatore("admin", "Marco", "Polo");
        operatore.setUsername("admin");


        rigaOrdine = new HashSet<>();
        final double prezzo = 10.50;
        servizio = new Servizio("Lavaggio", "secco", "veloce", prezzo);
        final int x = 3;
        servizio.setId(servizio.getId());
        servizio.setId(x);

        final int z = 6;
        sede = new Sede("Ariano Irpino, via Cardito, 52");
        sede.setId(z);
        sede2= new Sede("Ariano Irpino, corso Vittorio Emanuele, 250");
        sede2.setId(z+1);

        propostaModifica = new PropostaModifica();
        propostaModifica.setId(4);
        propostaModifica.setDataProposta(LocalDate.of(2022, 03, 8));

        final int y = 2022;
        final int m = 03;
        final int d = 02;
        final int id_o = 3;
        LocalDate data1 = LocalDate.of(y, m, d);
        ordine = new Ordine(propostaModifica.getDataProposta(), "domicilio", "Pronto");
        ordine.setSede(sede);
        ordine.setNote("blue");
        ordine.setId(id_o);
        ordine.setCliente(cliente);
        //ordine.getCliente().setEmail(cliente.getEmail());
        ordine.setRigheOrdine( rigaOrdine);
        ordine.setPropostaModifica(propostaModifica);



    }

    @Test
    public void listaOrdiniTest() throws Exception{
        List<Ordine> list= new ArrayList<>();
        list.add(ordine);
        when(ordiniService.visualizzaOrdiniTotali()).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                .param("filter","Totali")
                .sessionAttr("utente",u))
                .andExpect(model().attribute("ordini", list))
                .andExpect(view().name("ordini/ListaOrdini"));

       when(ordiniService.visualizzaOrdiniFiltroOperatore("Attivi")).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter","Attivi")
                        .sessionAttr("utente",u))
                .andExpect(model().attribute("ordini",list))
                .andExpect(view().name("ordini/ListaOrdini"));


        when(ordiniService.visualizzaOrdiniFiltroOperatore("ProvaNull")).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter","ProvaNull")
                        .sessionAttr("utente",u))
                .andExpect(model().attribute("ordini",list))
                .andExpect(view().name("ordini/ListaOrdini"));



        when(ordiniService.visualizzaOrdiniFiltroUtente("Attivi",ucliente.getCellulare())).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter","")
                        .sessionAttr("utente", ucliente))
                .andExpect(model().attribute("ordini",list))
               .andExpect(view().name("ordini/ListaOrdini"));


        when(ordiniService.visualizzaOrdiniFiltroUtente("Totali",ucliente.getCellulare())).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter","Totali")
                        .sessionAttr("utente", ucliente))
                .andExpect(model().attribute("ordini",list))
                .andExpect(view().name("ordini/ListaOrdini"));


  }

    @Test
    public void modificaStatoTest() throws Exception{
        List <Ordine> list= new ArrayList<>();
        list.add(ordine);

        when(ordiniService.findById(ordine.getId())).thenReturn(ordine);
        when(ordiniService.modificaOrdine(null, null,"Pronto", ordine.getId())).thenReturn(true);
        this.mockMvc.perform(post("/ordini/ListaOrdini/ModificaOrdine")
                        .param("stato","Pronto")
                        .param("idOrdine", String.valueOf(ordine.getId()))
                        .sessionAttr("utente",u))
                        .andExpect(view().name("ordini/ListaOrdini"));
    }

    @Test
    public void modificaStatoFail() throws Exception{
        Cliente c = new Cliente();
        ordine.setCliente(c);
        when(ordiniService.findById(ordine.getId())).thenReturn(ordine);
        when(ordiniService.modificaOrdine(null, null,"Pronto", ordine.getId())).thenReturn(true);
        this.mockMvc.perform(post("/ordini/ListaOrdini/ModificaOrdine")
                        .param("stato","Pronto")
                        .param("idOrdine", String.valueOf(ordine.getId()))
                        .sessionAttr("utente",u))
                .andExpect(view().name("ordini/ListaOrdini"));
    }

    @Test
    public void aggiuntaOrdineTest() throws Exception{
        Set<RigaOrdine> righe = new HashSet<>();
        RigaOrdine riga = new RigaOrdine(3);
        righe.add(riga);

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
                        .param("quantity","3")
                        .sessionAttr("utente",u))
                .andExpect(model().attribute("righe", righe))
                .andExpect(model().attribute("servizi", lists))
                .andExpect(model().attribute("sedi", list))
                .andExpect(model().attribute("clienti", listc))
                .andExpect(view().name("ordini/aggiuntaOrdine"));

        LocalDate data = LocalDate.of(2022, 03, 02);
        Ordine o = new Ordine();
        when(ordiniService.creazioneOrdine(righe, cliente.getUsername(), "domicilio", sede.getIndirizzo(), data, "prova")).thenReturn(o);

        this.mockMvc.perform(post("/ordini/aggiuntaOrdine")
                        .param("cliente","user")
                        .param("ritiro","domicilio")
                        .param("sedeDesiderata","Ariano Irpino, via Cardito, 52")
                        .param("date", String.valueOf(data))
                        .param("note","prova")
                        .sessionAttr("utente",u))
                .andExpect(view().name("/LoggedHomepage"));

    }

    @Test
    public void aggiuntaOrdineTestFail() throws Exception{

        LocalDate data = LocalDate.of(2022, 03, 02);

        this.mockMvc.perform(post("/ordini/aggiuntaOrdine")
                        .param("cliente","user")
                        .param("ritiro","domicilio")
                        .param("sedeDesiderata","Ariano Irpino, via Cardito, 52")
                        .param("date", String.valueOf(data))
                        .param("note","prova")
                        .sessionAttr("utente",u))
                .andExpect(view().name("error/500"));

    }


/*

    @Test
    public void modificaOrdine() throws Exception{

        Cliente cliente2 = new Cliente("user22", "user", "via rossi ", "Marco", "rossi");
        cliente2.setNumeroTelefono("222333777");


        Utente ucliente2 = new Utente("user22", "user");
        ucliente2.setCellulare(cliente2.getNumeroTelefono());
        ruolo = new Ruolo();
        ruolo.setName(ruolo.CLIENTE_ROLE);
        ucliente2.setRuolo(ruolo);
        ucliente2.setId(ucliente2.getId());


        final int y = 2022;
        final int m = 03;
        final int d = 02;
        final int id_o = 9;
        LocalDate data1 = LocalDate.of(y, m, d);
        Ordine ordine2 = new Ordine(data1, "domicilio", "Pronto");
        ordine2.setSede(sede);
        ordine2.setNote("blue");
        ordine2.setId(id_o);
        ordine2.setCliente(cliente2);
        //ordine.getCliente().setEmail(cliente.getEmail());
        ordine2.setRigheOrdine( rigaOrdine);

        List <Ordine> list= new ArrayList<>();
        list.add(ordine2);


       // ordine.getSede().setIndirizzo(sede2.getIndirizzo());
       // when(ordiniService.findById(ordine2.getId())).thenReturn(ordine2);
        when(ordiniService.findByIndirizzo(ordine2.getSede().getIndirizzo())).thenReturn(sede);
       // when(ordiniService.modificaOrdine(null, sede2 ,null, ordine.getId())).thenReturn(true);
        System.out.println("id " + ordine2.getId() + " telefono " + ordine2.getCliente());

        this.mockMvc.perform(post("/ordini/ListaOrdini/ModificaSede")
                        .param("idOrdine", String.valueOf(ordine2.getId()))
                        .sessionAttr("utente",u))
                        .andExpect(view().name("ordini/ListaOrdini"));

    }
*/

/*
    @Test
    public void ValutazioneAccetta() throws Exception{
        List <Ordine> list= new ArrayList<>();
        list.add(ordine);
        ordine.setDataConsegnaDesiderata(LocalDate.of(2022,02, 13));
        ordine.setStato("Macchiato");
        when(ordiniService.findById(ordine.getId())).thenReturn(ordine);
        when(ordiniService.findByIdProposta(ordine.getPropostaModifica().getId())).thenReturn(propostaModifica);
        System.out.println("id Proposta " +ordine.getPropostaModifica().getId() + "\nid ordine " + ordine.getId()
                + "\n Cliente " + ordine.getCliente().getNumeroTelefono() + "\nData "+ propostaModifica.getDataProposta());


        }
*/


    /*@Test
    public void ValutazioneRifiuta() throws Exception{
        this.mockMvc.perform(post("/ordini/ValutazioneRifiuta")
                        .param("rifiuta", String.valueOf(ordine.getId()))
                        .sessionAttr("utente", u))
                .andExpect(view().name("ordini/ListaOrdini"));
    }*/
}