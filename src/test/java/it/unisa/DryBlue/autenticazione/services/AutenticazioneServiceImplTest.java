package it.unisa.DryBlue.autenticazione.services;


import it.unisa.DryBlue.autenticazione.dao.OperatoreDAO;
import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AutenticazioneServiceImplTest {

    private Utente cliente, oper;
    private Cliente cliente1;
    private Operatore operatore;
    private Ruolo ruolo, ruolo1;

    @Mock
    private UtenteDAO utenteDAO;

    @Mock
    private ClienteDAO clienteDAO;

    @Mock
    private OperatoreDAO operatoreDAO;

    @Mock
    private AutenticazioneServiceImpl autenticazioneService;

    @BeforeEach
    public void init() {
        autenticazioneService = new AutenticazioneServiceImpl(utenteDAO, clienteDAO, operatoreDAO);

        ruolo = new Ruolo("CLIENTE");
        cliente = new Utente("user2", "user22");
        cliente.setNome("Carla");
        cliente.setCognome("Ricci");
        cliente.setIndirizzo("via Roma 12 Salerno");
        cliente.setCellulare("395566777");
        cliente.setRuolo(ruolo);

        cliente1 = new Cliente("user2", "user22", "via Roma 12 Salerno","Carla", "Ricci");
        cliente1.setNumeroTelefono("395566777");


        ruolo1 = new Ruolo("OPERATORE");
        oper = new Utente("user3", "user32");
        oper.setNome("Fabio");
        oper.setCognome("Ricci");
        oper.setIndirizzo("via Rossi 13 Napoli");
        oper.setCellulare("3334446661");
        oper.setRuolo(ruolo1);

        operatore = new Operatore("user32", "Fabio", "Ricci");
        operatore.setUsername("user3");
    }

    @Test
    public void loginTest() {
        String userOperatore = "user3";
        String pwd = "user32";
        oper = utenteDAO.findByUsername(userOperatore);
        assertEquals(utenteDAO.findByPassword(pwd), oper);
        assertEquals(utenteDAO.findByUsername(userOperatore), oper);
        operatore= operatoreDAO.findByUsername(userOperatore);
        assertEquals(operatoreDAO.findByUsername(userOperatore),operatore);
        assertEquals(autenticazioneService.login(userOperatore, pwd), operatore);

        userOperatore = "user3";
        pwd ="ur3";
        oper = utenteDAO.findByUsername(userOperatore);
        assertEquals(utenteDAO.findByPassword(pwd), oper);
        assertEquals(utenteDAO.findByUsername(userOperatore), oper);
        operatore= operatoreDAO.findByUsername(userOperatore);
        assertEquals(operatoreDAO.findByUsername(userOperatore),operatore);
        assertEquals(autenticazioneService.login(userOperatore, pwd), operatore);

        userOperatore = "ur3";
        pwd ="user32";
        oper = utenteDAO.findByUsername(userOperatore);
        assertEquals(utenteDAO.findByPassword(pwd), oper);
        assertEquals(utenteDAO.findByUsername(userOperatore), oper);
        operatore= operatoreDAO.findByUsername(userOperatore);
        assertEquals(operatoreDAO.findByUsername(userOperatore),operatore);
        assertEquals(autenticazioneService.login(userOperatore, pwd), operatore);

        String userCliente = "user2";
        String pwd1 = "user22";
        cliente = utenteDAO.findByUsername(userCliente);
        assertEquals(utenteDAO.findByPassword(pwd1), cliente);
        assertEquals(utenteDAO.findByUsername(userCliente), cliente);
        cliente1= clienteDAO.findByUsername(userCliente);
        assertEquals(clienteDAO.findByUsername(userCliente),cliente1);
        assertEquals(autenticazioneService.login(userCliente, pwd1), cliente);

        userCliente = "us";
        pwd1 ="user22";
        cliente = utenteDAO.findByUsername(userCliente);
        assertEquals(utenteDAO.findByPassword(pwd1), cliente);
        assertEquals(utenteDAO.findByUsername(userCliente), cliente);
        cliente1= clienteDAO.findByUsername(userCliente);
        assertEquals(clienteDAO.findByUsername(userCliente),cliente1);
        assertEquals(autenticazioneService.login(userCliente, pwd1), cliente);

        userCliente = "user2";
        pwd1 ="us2";
        cliente = utenteDAO.findByUsername(userCliente);
        assertEquals(utenteDAO.findByPassword(pwd1), cliente);
        assertEquals(utenteDAO.findByUsername(userCliente), cliente);
        cliente1= clienteDAO.findByUsername(userCliente);
        assertEquals(clienteDAO.findByUsername(userCliente),cliente1);
        assertEquals(autenticazioneService.login(userCliente, pwd1), cliente);

        userCliente = "u2";
        pwd1 ="us2";
        cliente = utenteDAO.findByUsername(userCliente);
        assertEquals(utenteDAO.findByPassword(pwd1), cliente);
        assertEquals(utenteDAO.findByUsername(userCliente), cliente);
        cliente1= clienteDAO.findByUsername(userCliente);
        assertEquals(clienteDAO.findByUsername(userCliente),cliente1);
        assertEquals(autenticazioneService.login(userCliente, pwd1), cliente);
    }

}




