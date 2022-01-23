package it.unisa.DryBlue.servizi.services;

import it.unisa.DryBlue.DryBlueApplication;
import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.dao.RigaOrdineDAO;
import it.unisa.DryBlue.ordini.dao.SedeDAO;
import it.unisa.DryBlue.ordini.domain.Etichetta;
import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.ordini.domain.RigaOrdine;
import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.servizi.dao.MacchinarioDAO;
import it.unisa.DryBlue.servizi.dao.ServizioDAO;
import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


@SpringBootTest
public class ServizioServiceImplIT {


    @Autowired
    private ServizioDAO servizioDAO;

    @Autowired
    private MacchinarioDAO macchinarioDAO;

    @Autowired
    private SedeDAO sedeDAO;



    @Autowired
    private RigaOrdineDAO rigaDao;

    private  ServizioService servizioService;

    private Servizio servizio1, servizio2;

    private Macchinario macchinario1;

    private Sede sede1;

    private RigaOrdine riga;

    @BeforeEach
    public  void init() {



        servizioService = new ServizioServiceImpl(servizioDAO, macchinarioDAO);
        final double prezzo1 = 10.5;
        final double prezzo2 = 6.5;
        servizio1 = new Servizio("Leggins", "lavaggio", "lavaggio a secco", prezzo1);
        //servizio1.setId(200);


        System.out.println("id "+ servizio1.getId());

        sede1 = new Sede("Ariano Irpino, via Cardito, 52");
        sedeDAO.save(sede1);
        macchinario1 = new Macchinario("Lavatrice12", "LavatriceIndustriale", "Whirlpool", "Mario Rossi",
                "333222333222", "In funzione");
        macchinario1.setMatricola("AB12345");
        macchinario1.setSede(sede1);




        List<Servizio> s =(List<Servizio>) servizioDAO.findAll();

        for ( Servizio e: s) {
            System.out.println(e);
        }
    }

    @AfterEach
    void afterEach() {

       //servizioDAO.deleteAll();
       // rigaDao.deleteAll();
        macchinarioDAO.deleteAll();
       //sedeDAO.deleteAll();

    }

    @Test
    public void aggiungiServizioSuccess() {
        final double prezzo = 10.5;

        Servizio inserito =servizioService.aggiungiServizio("Leggins", "lavaggio", "lavaggio a secco", prezzo);

        List<Servizio> servizios = (List<Servizio>)servizioDAO.findAll();
        assertTrue(servizios.contains(inserito));
    }

    @Test
    public void findServiziSuccess() {
        List<Servizio> list = new ArrayList<>();


        servizioDAO.save(servizio1);


        list=(List<Servizio>)servizioDAO.findAll();
        assertEquals(servizioService.findServizi(), list);
    }

    @Test
    public void aggiornaStatoMacchinarioSuccess() {


        Macchinario added =macchinarioDAO.save(macchinario1);
        servizioService.aggiornaStatoMacchinario(macchinario1.getMatricola(),"Inattivo");

        assertEquals(macchinarioDAO.findById(added.getMatricola()).get().getStato(), macchinarioDAO.findById(macchinario1.getMatricola()).get().getStato());

    }

    @Test
    public void aggiornaStatoMacchinarioFail() {

        assertEquals(servizioService.aggiornaStatoMacchinario(macchinario1.getMatricola(),"Inattivo"), false);
    }


}
