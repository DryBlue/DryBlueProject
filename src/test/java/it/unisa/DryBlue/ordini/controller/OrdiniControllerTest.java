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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import java.util.Set;


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


        u = new Utente("admin", "admin");
        ruolo = new Ruolo();
        ruolo.setName(ruolo.OPERATORE_ROLE);
        u.setRuolo(ruolo);
        u.setId(3);

        operatore = new Operatore("admin", "Marco", "Polo");
        operatore.setUsername("admin");


        rigaOrdine = new RigaOrdine(2);
        final double prezzo = 10.50;
        servizio = new Servizio("Lavaggio", "secco", "veloce", prezzo);
        final int x = 3;
        servizio.setId(servizio.getId());

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
        ordine.setId(3);

    }

   /* @Test
    public void listaOrdiniTest() throws Exception{

        List <Ordine> list= new ArrayList<>();
        list.add(ordine);
        when(ordiniService.visualizzaOrdiniTotali()).thenReturn(list);
        this.mockMvc.perform(post("/ListaOrdini")
                .param("filter","Totali")
                .sessionAttr("utente",u))
                .andExpect(model().attribute("ordini",list))
                .andExpect(view().name("/ordini/ListaOrdini"));

       when(ordiniService.visualizzaOrdiniFiltroOperatore("Attivi")).thenReturn(list);
        this.mockMvc.perform(post("/ListaOrdini")
                        .param("filter","Attivi")
                        .sessionAttr("utente",u))
                .andExpect(model().attribute("ordini",list))
                .andExpect(view().name("/ordini/ListaOrdini"));

        when(ordiniService.visualizzaOrdiniFiltroUtente("Attivi",ucliente.getCellulare())).thenReturn(list);
        this.mockMvc.perform(post("/ListaOrdini")
                        .param("filter","Attivi")
                        .sessionAttr("utente",ucliente))
                .andExpect(model().attribute("ordini",ucliente.getCellulare()))
                .andExpect(view().name("/ordini/ListaOrdini"));

  }

    @Test
    public void modificaStatoTest() throws Exception{
        List <Ordine> list= new ArrayList<>();
        list.add(ordine);
        final int y = 2022;
        final int m = 03;
        final int d = 02;
        Sede sede2= new Sede("Ariano Irpino, AV, via Cardito, 52");
        Integer id_ordine = ordine.getId();
        LocalDate data = LocalDate.of(y, m, d);

        when(ordiniService.modificaOrdine(data, sede2,"Pronto", 3)).thenReturn(true);
        this.mockMvc.perform(post("/ListaOrdini/ModificaOrdine")
                .param("stato","Pronto")
                .param("idOrdine", "3")
                .sessionAttr("utente",u))
                .andExpect(view().name("/ordini/ListaOrdini"));

    }

    @Test
    public void modificaOrdine() throws Exception{
        List <Ordine> list= new ArrayList<>();
        list.add(ordine);
        Integer id_ordine = ordine.getId();
        Sede sede2= new Sede("Ariano Irpino, AV, via Cardito, 52");
        final int yP = 2022;
        final int mP = 11;
        final int dP = 02;
        LocalDate data = LocalDate.of(yP,mP,dP);

        when(ordiniService.modificaOrdine(data, sede2,"Macchiato", id_ordine)).thenReturn(true);
        this.mockMvc.perform(post("/ordini/ListaOrdini/ModificaSede")
                        .param("idOrdine", "3")
                        .sessionAttr("utente", u)
                .sessionAttr("utente", ucliente))
                .andExpect(view().name("/ordini/ListaOrdini?filter=Attivi"));
    }

    @Test
    public void ValutazioneAccetta() throws Exception{

        when(ordiniService.findById(ordine.getId())).thenReturn(Optional.ofNullable(ordine));
        this.mockMvc.perform(post("/ordini/ValutazioneAccetta")
                        .param("idOrdine", "3")
                        .sessionAttr("utente", u))
                .andExpect(view().name("/ordini/ListaOrdini"));

    }

    @Test
    public void ValutazioneRifiuta() throws Exception{

    }*/
}