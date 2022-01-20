package it.unisa.DryBlue.gestioneCliente.controller;

import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class GestioneClienteControllerTest {

    @MockBean
    private GestioneClienteService gestioneClienteService;

    @Autowired
    private MockMvc mockMvc;

    private Cliente cliente;

    private Utente u;
    private Ruolo ruolo;



    @BeforeEach
    public void init() {

        u = new Utente("user", "pwd");
        ruolo = new Ruolo();
        ruolo.setName(ruolo.OPERATORE_ROLE);
        u.setRuolo(ruolo);
        u.setCellulare("333444888");

        cliente = new Cliente();
        cliente.setUsername("user");
        cliente.setPassword("pwd");
        cliente.setNumeroTelefono("333444888");


    }

    @Test
    public void trovaTuttiIClienti() throws Exception{
        List<Cliente> list = new ArrayList<>();
        list.add(cliente);
        when(gestioneClienteService.findTuttiIClienti()).thenReturn(list);
        this.mockMvc.perform(get("/gestioneCliente/clienti")
                        .sessionAttr("utente", u))
                .andExpect(model().attribute("clienti", list))
                .andExpect(view().name(
                        "/gestioneCliente/ListaClienti"));
    }



    @Test
    public void dettagliCliente() throws Exception{
        List<Cliente> list = new ArrayList<>();
        list.add(cliente);
        when(gestioneClienteService.findTuttiIClienti()).thenReturn(list);
        this.mockMvc.perform(get("/gestioneCliente/clienti")
                        .sessionAttr("utente", u))
                .andExpect(model().attribute("clienti", list))
                .andExpect(view().name(
                        "/gestioneCliente/ListaClienti"));
    }
    /*
    @Test
    public void dettagliCliente() throws Exception{
        String telefono= cliente.getNumeroTelefono();
        when(gestioneClienteService.findByTelefono(telefono)).thenReturn(cliente);
        this.mockMvc.perform(get("/gestioneCliente/clienti/dettagliCliente")
                .sessionAttr("utente", u))
                .andExpect(model().attribute("clientela", cliente))
               .andExpect(view().name(
                "/gestioneCliente/DettagliCliente"));
    }*/


    @Test
    public void dettagli() throws Exception {
        String telefono = cliente.getNumeroTelefono();
        when(gestioneClienteService.findByTelefono(telefono)).thenReturn(cliente);
        this.mockMvc.perform(post("/gestioneCliente/clienti/dettagliCliente")
                        .param("telefono", "333444888")
                        .sessionAttr("utente", u))
                .andExpect(model().attribute("clientela", cliente))
                .andExpect(view().name(
                        "/gestioneCliente/DettagliCliente"));

    }
/*
    @GetMapping(value = "/clienti/dettagliCliente")
    public String dettagliCliente(final Model model) {
        model.getAttribute("utente");
        return "/gestioneCliente/DettagliCliente";
    }

    @PostMapping("/clienti/dettagliCliente")
    public String dettagli(@RequestParam("telefono") final String numTel, final Model model) {
        Cliente c = gestioneClienteService.findByTelefono(numTel);
        model.addAttribute("clientela", c);
        return "/gestioneCliente/DettagliCliente";
    }
}*/


}
