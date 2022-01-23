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

    private Utente cliente, oper,user;
    private Cliente cliente1;
    private Operatore operatore;
    private Ruolo ruolo, ruolo1,ruolo2;

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

        ruolo2 = new Ruolo();
        user = new Utente("x", "x");
        user.setRuolo(ruolo2);



        ruolo1 = new Ruolo("OPERATORE");
        oper = new Utente("user32", "user32");
        oper.setUsername("user32");
        oper.setPassword("user32");
        oper.setNome("Fabio");
        oper.setCognome("Ricci");
        oper.setUsername("user32");
        oper.setRuolo(ruolo1);


    }


    @Test
    public void loginClienteTestSuccess() {
        //cliente ok
        cliente1 = new Cliente("user2", "user22", "via Roma 12 Salerno", "Carla", "Ricci");
        cliente1.setNumeroTelefono("395566777");
        cliente.setCellulare("395566777");
        cliente.setUsername(cliente1.getUsername());
        cliente.setPassword(cliente1.getPassword());
        String username_c="user2";
        String pwdcliente="user22";
        when(clienteDAO.findByUsername(username_c)).thenReturn(cliente1);
        assertEquals(autenticazioneService.login(username_c, pwdcliente), cliente);

        //operatore ok
        operatore = new Operatore("user32", "Fabio", "Ricci");
        operatore.setUsername("user32");
        operatore.setPassword("user32");
        oper.setUsername(operatore.getUsername());
        oper.setPassword(operatore.getPassword());
        String userOperatore = "user32";
        String pwd = "user32";
        when(operatoreDAO.findByUsername(userOperatore)).thenReturn(operatore);
        assertEquals(autenticazioneService.login(userOperatore, pwd), oper);

    }

    @Test
    public void loginFail() {
        String userUser = "x";
        String pwd = "x";

        when(operatoreDAO.findByUsername(userUser)).thenReturn(null);
        assertEquals(autenticazioneService.login(userUser, pwd), user);
    }

}
