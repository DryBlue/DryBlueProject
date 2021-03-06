package it.unisa.DryBlue.autenticazione.services;

import it.unisa.DryBlue.autenticazione.dao.OperatoreDAO;
import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Ruolo;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AutenticazioneServiceImplIT {

    private Utente cliente;
    private Utente oper;
    private Cliente cliente1;
    private Operatore operatore;
    private Ruolo ruolo, ruolo1;
    private AutenticazioneService autenticazioneService;

    @Autowired
    private UtenteDAO utenteDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private OperatoreDAO operatoreDAO;


    @BeforeEach
    public void init() {
        autenticazioneService = new AutenticazioneServiceImpl(utenteDAO, clienteDAO, operatoreDAO);

        ruolo = new Ruolo("CLIENTE");
        cliente = new Utente("user11", "user11");
        cliente.setNome("Carla");
        cliente.setCognome("Ricci");
        cliente.setIndirizzo("via Roma 12 Salerno");
        cliente.setCellulare("395566777");
        cliente.setRuolo(ruolo);

        cliente1 = new Cliente("user2", "user22", "via Roma 12 Salerno", "Carla", "Ricci");
        cliente1.setNumeroTelefono("395566777");
        clienteDAO.save(cliente1);


        ruolo1 = new Ruolo("OPERATORE");
        oper = new Utente("user3", "user32");
        oper.setNome("Fabio");
        oper.setCognome("Ricci");
        oper.setRuolo(ruolo1);

        operatore = new Operatore("user32", "Fabio", "Ricci");
        operatore.setUsername("user3");
        /*List<Utente> list= (List<Utente>) utenteDAO.findAll();
        for (Utente ut: list){

            System.out.println(ut);
        }*/

    }

    @AfterEach
    void afterEach() {
        //utenteDAO.deleteAll();
    }

    @Test
    void loginClienteTest() {
        String clienteuser = "user2";
        Utente c = utenteDAO.findByUsername("user2");
        Utente utenteInserito = autenticazioneService.login(clienteuser, "user2");
        assertEquals(c, utenteInserito);
    }
}
