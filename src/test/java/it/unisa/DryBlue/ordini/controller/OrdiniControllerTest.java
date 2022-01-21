package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.domain.*;
import it.unisa.DryBlue.ordini.services.OrdiniService;
import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;
import it.unisa.DryBlue.servizi.services.ServizioService;
import it.unisa.DryBlue.servizi.services.ServizioServiceImpl;
import org.apache.catalina.LifecycleState;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    private Sede sede;
    private RigaOrdine rigaOrdine;
    private PropostaModifica propostaModifica;
    private Cliente cliente;
    private Utente u;
    private Ruolo ruolo;
    private Operatore operatore;
    private Servizio servizio;
    private Set<RigaOrdine> righe;


    @BeforeEach
    public void init() {
        u = new Utente("user", "user");
        ruolo = new Ruolo();
        ruolo.setName(ruolo.CLIENTE_ROLE);
        u.setRuolo(ruolo);

        cliente = new Cliente("user", "user", "via verdi ", "Marco", "rossi");
        cliente.setNumeroTelefono("222333666");


        rigaOrdine = new RigaOrdine(2);
        final double prezzo= 10.50;
        servizio = new Servizio("Lavaggio", "secco", "veloce", prezzo);
        servizio.setId(3);

        sede = new Sede();
        sede.setIndirizzo("Ariano Irpino, via Cardito, 52");

        LocalDate data1 = LocalDate.of(2022, 03, 02);
        ordine = new Ordine(data1, "domicilio", "macchiato");
        ordine.getSede().setIndirizzo(sede.getIndirizzo());
        ordine.setNote("");
        ordine.setCliente(cliente);


    }


    @Test
    public void aggiuntaRiga() throws Exception{
    List<RigaOrdine> list = new ArrayList<>();
    list.add(rigaOrdine);
        when(servizioService.findServizioById(servizio.getId())).thenReturn(servizio);
        this.mockMvc.perform(post("/ordini/aggiuntaRiga")
                        .param("idServizio", "3")
                        .param("quantity", "2")
                        .sessionAttr("utente", u))
                .andExpect(model().attribute("righe", list))
                .andExpect(model().attribute("servizi", servizio))
                .andExpect(model().attribute("sedi", "Ariano Irpino, via Cardito, 52"))
                .andExpect(model().attribute("clienti", cliente))
                .andExpect(view().name(
                        "ordini/aggiuntaOrdine"));
    }
}


