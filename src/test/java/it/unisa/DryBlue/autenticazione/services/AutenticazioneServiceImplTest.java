package it.unisa.DryBlue.autenticazione.services;

import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AutenticazioneServiceImplTest {

    private Utente cliente, oper;
    private Ruolo ruolo, ruolo1;

    @Mock
    private UtenteDAO utenteDAO;

    @BeforeEach
    public void init() {
        ruolo = new Ruolo("CLIENTE");
        cliente = new Utente("user2", "user2");
        cliente.setNome("Carla");
        cliente.setCognome("Ricci");
        cliente.setIndirizzo("via Roma 12 Salerno");
        cliente.setCellulare("395566777");
        cliente.setRuolo(ruolo);

        ruolo1 = new Ruolo("OPERATORE");
        oper = new Utente("user3", "user3");
        oper.setNome("Marco");
        oper.setCognome("Ricci");
        oper.setIndirizzo("via Roma 12 Salerno");
        oper.setCellulare("395566777");
        oper.setRuolo(ruolo1);

    }

    @Test
    public void loginTest(){
        String userOperatore = "user3";
        String pwd ="user32";
        oper = utenteDAO.findByUsername(oper.getUsername());
        assertEquals(utenteDAO.findByPassword(pwd), oper);
        assertEquals(utenteDAO.findByUsername(userOperatore), oper);

        String userCliente = "user2";
        String pwd1 ="user22";
        cliente = utenteDAO.findByUsername(cliente.getUsername());
        assertEquals(utenteDAO.findByPassword(pwd1), cliente);
        assertEquals(utenteDAO.findByUsername(userCliente), cliente);
    }
    }
