package it.unisa.DryBlue.servizi;

/**
 * DA TESTARE METODI rimuoviMacchinario, aggiornaStatoMacchinario, rimuoviServizio, modificaServizio
 */

import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.servizi.dao.MacchinarioDAO;
import it.unisa.DryBlue.servizi.dao.ServizioDAO;
import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;
import it.unisa.DryBlue.servizi.services.ServizioService;
import it.unisa.DryBlue.servizi.services.ServizioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


    @SpringBootTest
    @ExtendWith(MockitoExtension.class)
    public class ServizioServiceImplTest {
        @Mock
        private ServizioDAO servizioDAO;

        @Mock
        private MacchinarioDAO macchinarioDAO;

        private ServizioService servizioService;

        private Servizio servizio1, servizio2;

        private Macchinario macchinario1;

        private Sede sede1;

        @BeforeEach
        public void init() {
            servizioService = new ServizioServiceImpl(servizioDAO, macchinarioDAO);
            final double prezzo1 = 10.5;
            final double prezzo2 = 6.5;
            servizio1 = new Servizio("Leggins", "lavaggio", "lavaggio a secco", prezzo1);
            servizio2 = new Servizio("Giacca", "stiratura", "stiratura base", prezzo2);
            sede1 = new Sede("Ariano Irpino, via Cardito, 52");
            macchinario1 = new Macchinario("Lavatrice12", "LavatriceIndustriale", "Whirlpool", "Mario Rossi",
                    "333222333222", "In funzione");
            macchinario1.setMatricola("AB1234");
            macchinario1.setSede(sede1);
        }

          /**
           * Metodo che testa il caso in cui l'aggiunta di un Servizio va a buon fine
           *
           * @result Il test è superato se il metodo save del DAO è correttamente invocato
           */
        @Test
        public void aggiungiServizioSuccess() {
            final double prezzo = 10.5;
            when(servizioDAO.save(servizio1)).thenReturn(servizio1);
            servizioService.aggiungiServizio("Leggins", "lavaggio", "lavaggio a secco",prezzo);
            verify(servizioDAO, times(1)).save(servizio1);
        }

        /**
         * Metodo che testa il caso in cui la ricerca dei Servizi va a buon fine
         * @result Il test è superato se il metodo findAll del DAO è correttamente invocato
         */
        @Test
        public void findServiziSuccess() {
            List<Servizio> list = new ArrayList<>();
            list.add(servizio1);
            list.add(servizio2);
            when(servizioDAO.findAll()).thenReturn(list);
            assertEquals(servizioService.findServizi(), list);
        }

        /**
         * Metodo che testa il caso in cui l'aggiunta di un macchinario va a buon fine
         * @result Il test è superato se il metodo save del DAO è correttamente invocato
         */

        @Test
        public void aggiungiMacchinarioTest(){
            when(macchinarioDAO.save(macchinario1)).thenReturn(macchinario1);
            servizioService.aggiungiMacchinario("AB1234", "Lavatrice12", "LavatriceIndustriale",
                    "Whirlpool", "Mario Rossi", "333222333222", "In Funzione", sede1);
            verify(macchinarioDAO, times(1)).save(macchinario1);

        }

        @Test
        public void findMacchinariTest() {
            List<Macchinario> list = new ArrayList<>();
            list.add(macchinario1);
            when(macchinarioDAO.findAll()).thenReturn(list);
            assertEquals(servizioService.findMacchinari(), list);
        }

        @Test
        public void aggiornaStatoMacchinarioSuccess() {
            when(macchinarioDAO.findById("AB1234")).thenReturn(Optional.ofNullable(macchinario1));
            when(macchinarioDAO.save(macchinario1)).thenReturn(macchinario1);
            assertEquals(servizioService.aggiornaStatoMacchinario("AB1234", "In funzione"), true);
        }

        @Test
        public void aggiornaStatoMacchinarioFail() {
            when(macchinarioDAO.findById("")).thenReturn(Optional.empty());
            assertEquals(servizioService.aggiornaStatoMacchinario("", "In funzione"), false);
        }


    }
