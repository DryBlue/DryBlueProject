package it.unisa.DryBlue.autenticazione.controller;

import it.unisa.DryBlue.autenticazione.dao.OperatoreDAO;
import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.autenticazione.services.AutenticazioneService;
import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class AutenticazioneControllerTest {

/*
    @MockBean
    private AutenticazioneService autenticazioneService;

    @Autowired
    private MockMvc mockMvc;

    private Utente utente, utente1;
    private Cliente cliente;
    private Operatore operatore;
    private Ruolo ruolo, ruolo1;

    @Mock
    private UtenteDAO utenteDAO;

    @Mock
    private ClienteDAO clienteDAO;

    @Mock
    private OperatoreDAO operatoreDAO;


    @BeforeEach
    public void init() {


         utente = new Utente("user2", "user22");
        cliente= new Cliente();
        cliente.setUsername(utente.getUsername());
        cliente.setPassword(utente.getPassword());


        ruolo1 = new Ruolo("OPERATORE");
        utente1 = new Utente("user3", "user32");
        utente1.setNome("Fabio");
        utente1.setCognome("Ricci");
        utente1.setRuolo(ruolo1);

        operatore = new Operatore("user32", "Fabio", "Ricci");
        operatore.setUsername("user3");
    }

    @Test
    public void loginTest() throws Exception {

        when(autenticazioneService.login("user2", "user22")).thenReturn(utente);
        this.mockMvc.perform(post("/login")
                        .param("username", "user2")
                        .param("password", "user22")
                        .sessionAttr("utente", utente))
                .andExpect(view().name(
                        "LoggedHomepage"));

    }
*/
}
