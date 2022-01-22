package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
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

        final int z = 6;
        sede = new Sede("Ariano Irpino, via Cardito, 52");
        sede.setId(z);
        sede2= new Sede("Ariano Irpino, corso Vittorio Emanuele, 250");
        sede2.setId(6);

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

        when(ordiniService.visualizzaOrdiniFiltroOperatore("Attivi")).thenReturn(list);
        this.mockMvc.perform(get("/ordini/ListaOrdini")
                        .param("filter","Attivi")
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
  /*
    @Test
    public void modificaOrdine() throws Exception{
        List <Ordine> list= new ArrayList<>();
        list.add(ordine);

        ordine.getSede().setIndirizzo(sede2.getIndirizzo());

        when(ordiniService.findById(ordine.getId())).thenReturn(ordine);
        when(ordiniService.findByIndirizzo(ordine.getSede().getIndirizzo())).thenReturn(sede);
        when(ordiniService.modificaOrdine(null, sede2 ,null, ordine.getId())).thenReturn(true);
        System.out.println("id " + ordine.getId() + " telefono " + ordine.getCliente());

        this.mockMvc.perform(post("/ordini/ListaOrdini/ModificaSede")
                        .param("idOrdine", String.valueOf(ordine.getId()))
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

        this.mockMvc.perform(post("/ordini/ValutazioneAccetta")
                        .param("accetta", String.valueOf(ordine.getId()))
                        .sessionAttr("utente", u))
                        .andExpect(view().name("ordini/ListaOrdini"));
        }
*/
    /*  @PostMapping("/ValutazioneAccetta")
    public String ValutazioneAccetta(final Model model,
                                     final @RequestParam("accetta") Integer accetta) {
        model.getAttribute("utente");
        Ordine ordine = ordiniService.findById(accetta);
        String email = ordine.getCliente().getEmail();

        Integer idProp = ordine.getPropostaModifica().getId();
        PropostaModifica pr = ordiniService.findByIdProposta(idProp);
        LocalDate data = pr.getDataProposta();
        System.out.println("data " + data);

        ordine.setDataConsegnaDesiderata(data);
        pr.setStato("Conclusa");

        String scelta = "ACCETTATA";
        if (email != null) {
            senderProposta.sendEmail(email, scelta);
        }
        propostaModificaDAO.save(pr);
        ordineDAO.save(ordine);
        return listaOrdini("Attivi", model);
    }*/

    @Test
    public void ValutazioneRifiuta() throws Exception{

    }
}