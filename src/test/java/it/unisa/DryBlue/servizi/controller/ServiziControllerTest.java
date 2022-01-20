package it.unisa.DryBlue.servizi.controller;

import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;
import it.unisa.DryBlue.servizi.services.ServizioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiziControllerTest {

    @MockBean
    private ServizioService servizioService;

    @Autowired
    private MockMvc mockMvc;

    private Macchinario m1, m2;
    private Servizio s1, s2;
    private Utente u;
    private Sede sede1;
    private Ruolo ruolo;

    @BeforeEach
    public void init() {
        sede1 = new Sede("Ariano Irpino, via Cardito, 52");
        sede1.setId(3);
        m1 = new Macchinario("Lavatrice12", "LavatriceIndustriale", "Whirlpool", "Mario Rossi",
                "333222333222", "In funzione");
        m1.setMatricola("AB1234");
        m1.setSede(sede1);
        m2 = new Macchinario("MacchinarioNuovo", "Grande", "Francesco", "Giacomo",
                "3345698541", "In funzione");
        m2.setMatricola("AB1");
        m2.setSede(sede1);
        u = new Utente("admin", "admin");
        ruolo = new Ruolo();
        ruolo.setName(ruolo.OPERATORE_ROLE);
        u.setRuolo(ruolo);

        final double prezzo1 = 10.5;
        s1 = new Servizio("Leggins", "lavaggio", "lavaggio a secco", prezzo1);

    }

    @Test
    public void trovaMacchinari() throws Exception {
        List<Macchinario> list = new ArrayList<>();
        list.add(m1);
        when(servizioService.findMacchinari()).thenReturn(list);
        this.mockMvc.perform(get("/servizio/ListaMacchinari")
                        .sessionAttr("utente", u))
                .andExpect(model().attribute("macchinari", list))
                .andExpect(view().name(
                        "/servizi/ListaMacchinari"));
    }

/*
    @Test
    public void aggiuntaMacchinario() throws Exception {
        when(servizioService.aggiungiMacchinario("AB1", "MacchinarioNuovo", "Grande", "Francesco", "Giacomo", "3345698541", "In funzione", sede1)).thenReturn(m2);
        this.mockMvc.perform(post("/servizio/aggiuntaMacchinario")
                        .param("denomination", "MacchinarioNuovo")
                        .param("matricola", "AB1")
                        .param("costruttore", "Francesco")
                        .param("caratteristiche", "Grande")
                        .param("manutentore", "Giacomo")
                        .param("numeroMan", "3345698541")
                        .param("sede", sede1.getIndirizzo())
                        .sessionAttr("utente", u))
                .andExpect(view().name("/servizi/ListaMacchinari"));
    }
*/

    @Test
    public void aggiornaStatoMacchinario() throws Exception {
        when(servizioService.aggiornaStatoMacchinario("AB1234", "In funzione")).thenReturn(true);
        this.mockMvc.perform(post("/servizio/aggiornaStatoMacchinario")
                        .param("matricola", "AB1234")
                        .param("stato", "In funzione"))
                .andExpect(view().name("/servizi/ListaMacchinari"));
    }

    @Test
    public void aggiuntaServizioPost() throws Exception {
        //Utente op = aut.login("admin", "admin");
        when(servizioService.aggiungiServizio("Leggins", "lavaggio", "lavaggio a secco", 10.5)).thenReturn(s1);
        this.mockMvc.perform(post("/servizio/aggiuntaServizio")
                        .param("name", "Leggins")
                        .param("tipologia", "lavaggio")
                        .param("caratteristiche", "lavaggio a secco")
                        .param("prezzo", String.valueOf(10.5))
                        .sessionAttr("utente", u))
                .andExpect(view().name("/servizi/ListaServizi"));
    }

    @Test
    public void aggiuntaServizioGet() throws Exception {
        this.mockMvc.perform(get("/servizio/aggiuntaServizio")
                        .sessionAttr("utente", u))
                .andExpect(view().name("/servizi/aggiuntaServizio"));
    }
}