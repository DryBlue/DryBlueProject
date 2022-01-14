package it.unisa.DryBlue.servizi;

import it.unisa.DryBlue.servizi.dao.MacchinarioDAO;
import it.unisa.DryBlue.servizi.dao.ServizioDAO;
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

import static org.mockito.Mockito.*;


    @SpringBootTest
    @ExtendWith(MockitoExtension.class)
    public class ServizioServiceImplTest{
        @Mock
        private ServizioDAO servizioDAO;

        @Mock
        private MacchinarioDAO macchinarioDAO;

        private ServizioService servizioService;

        private Servizio servizio1, servizio2;

        @BeforeEach
        public void init() {
            servizioService = new ServizioServiceImpl(servizioDAO, macchinarioDAO);
            servizio1 = new Servizio("Leggins", "lavaggio","lavaggio a secco",10.5);
            servizio2 = new Servizio("Giacca", "stiratura","stiratura base",6.5);
        }

          /**
           * Metodo che testa il caso in cui l'aggiunta di un Servizio va a buon fine
           *
           * @result Il test è superato se il metodo save del DAO è correttamente invocato
           */
        @Test
        public void aggiungiServizioSuccess() {
            when(servizioDAO.save(servizio1)).thenReturn(servizio1);
            servizioService.aggiungiServizio("Leggins", "lavaggio","lavaggio a secco",10.5);
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
    }