package it.unisa.DryBlue.autenticazione.controller;


import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.autenticazione.services.AutenticazioneService;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



//import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class AutenticazioneControllerTest {


    @MockBean
    private AutenticazioneService autenticazioneService;
    private GestioneClienteService gestioneClienteService;

    @Autowired
    private MockMvc mockMvc;

    private Utente utente, utente1, utente2;
    private Cliente cliente;
    private Operatore operatore;
    private Ruolo ruolo, ruolo1;


    @BeforeEach
    public void init() {


        ruolo = new Ruolo("CLIENTE");
        utente = new Utente("user2", "user22");
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setRuolo(ruolo);
        cliente = new Cliente();
        cliente.setUsername("user2");
        cliente.setPassword("user22");
        cliente.setEmail("ciao@gmail.com");
        cliente.setNumeroTelefono("3345678933");



        ruolo1 = new Ruolo("OPERATORE");
        utente1 = new Utente("user3", "user32");
        utente1.setNome("Fabio");
        utente1.setCognome("Ricci");
        utente1.setRuolo(ruolo1);

        operatore = new Operatore("user32", "Fabio", "Ricci");
        operatore.setUsername("user3");

        utente2 = new Utente();
        utente2.setUsername("x");
        utente2.setPassword("x");
        Ruolo x = new Ruolo("x");
        utente2.setRuolo(x);
    }

    @Test
    public void loginSuccess() throws Exception {

        when(autenticazioneService.login("user2", "user22")).thenReturn(utente);
        this.mockMvc.perform(post("/autenticazione/login")
                        .param("username", "user2")
                        .param("password", "user22")
                        .sessionAttr("utente", utente))
                .andExpect(view().name("redirect:/LoggedHomepage"));

    }

    @Test
    public void loginFail() throws Exception {
        when(autenticazioneService.login("pippaArSugo", "PieroDB")).thenReturn(utente2);
        this.mockMvc.perform(post("/autenticazione/login")
                        .param("username", "pippaArSugo")
                        .param("password", "PieroDB"))
                .andExpect(model().attribute("error", true))
                .andExpect(view().name("/autenticazione/Login"));
    }

    /*@Test
    public void updatePassword() throws Exception {
        this.mockMvc.perform(post("/autenticazione/newPassword")
                .param("username", "user2")
                .param("newPassword", "ciaone")
                .param("oldPassword", "utente22")
                .param("email", "ciao@gmail.com")
                        .sessionAttr("utente", utente))
                .andExpect(view().name("redirect:/"));

    }*/

}
