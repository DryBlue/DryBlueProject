
package it.unisa.DryBlue;

import it.unisa.DryBlue.autenticazione.dao.OperatoreDAO;
import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.dao.*;
import it.unisa.DryBlue.ordini.domain.*;
import it.unisa.DryBlue.servizi.dao.MacchinarioDAO;
import it.unisa.DryBlue.servizi.dao.ServizioDAO;
import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class DryBlueApplication {
    public static void main(final String[] args) {
        SpringApplication.run(DryBlueApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(final SedeDAO sedeDAO,
                                  final ClienteDAO clienteDAO,
                                  final OrdineDAO ordineDAO,
                                  final MacchinarioDAO macchinarioDAO,
                                  final ServizioDAO servizioDAO,
                                  final OperatoreDAO operatoreDAO,
                                  final RigaOrdineDAO rigaOrdineDAO,
                                  final EtichettaDAO etichettaDAO
    ) {
        return args -> {
            Sede sede1 = new Sede("Ariano Irpino, via Cardito, 52");
            Sede sede2 = new Sede("Ariano Irpino, corso Vittorio Emanuele, 250");
            sedeDAO.saveAll(Arrays.asList(sede1, sede2));

            Cliente cliente1 = new Cliente();
            cliente1.setNumeroTelefono("3456327821");
            cliente1.setUsername(cliente1.generateString());
            cliente1.setPassword(cliente1.generateString());
            cliente1.setNome("Mario");
            cliente1.setCognome("Rossi");
            cliente1.setIndirizzo("Salerno, SA, Via Roma, 15");

            Cliente cliente2 = new Cliente();
            cliente2.setNumeroTelefono("34563275671");
            cliente2.setUsername(cliente2.generateString());
            cliente2.setPassword(cliente2.generateString());
            cliente2.setNome("Felice");
            cliente2.setCognome("Russo");
            cliente2.setIndirizzo("Nola, NA, P.zza Duomo, 13");

            Cliente cliente3 = new Cliente();
            cliente3.setNumeroTelefono("34733275671");
            cliente3.setUsername("user");
            cliente3.setPassword("user");
            cliente3.setNome("Maria");
            cliente3.setCognome("Ruocco");
            cliente3.setIndirizzo("Ariano Irpino, AV, Via Cardito, 10");

            Cliente cliente4 = new Cliente();
            cliente4.setNumeroTelefono("3332994445");
            cliente4.setUsername("user1");
            cliente4.setPassword("user1");
            cliente4.setNome("Camilla");
            cliente4.setCognome("Bonomelli");
            cliente4.setIndirizzo("Fisciano, SA, Via Giovanni Paolo II, 21");
            cliente4.setEmail("m.ferrara115@studenti.unisa.it");
            clienteDAO.saveAll(Arrays.asList(cliente1, cliente2, cliente3, cliente4));

            /*Cliente cliente1 = new Cliente();
            cliente1.setNumeroTelefono("11122334444");
            cliente1.setUsername("user");
            cliente1.setPassword("user");
            cliente1.setIndirizzo("Roccarainola, via Roma, 3");
            cliente1.setNome("Mario");
            cliente1.setCognome("Rossi");

            Cliente cliente3 = new Cliente();
            cliente3.setNumeroTelefono("3332233222");
            cliente3.setUsername("user2");
            cliente3.setPassword("user2");
            cliente3.setIndirizzo("Nola, P.zza Duomo, 3");
            cliente3.setNome("Felice");
            cliente3.setCognome("Russo");
            clienteDAO.saveAll(Arrays.asList(cliente1, cliente3)); */

            final double prezzo1 = 2.20;
            Servizio servizio = new Servizio();
            servizio.setNome("Lavaggio maglietta");
            servizio.setTipologia("Lavaggio");
            servizio.setCaratteristiche("Maglietta di cotone");
            servizio.setPrezzo(prezzo1);

            final double prezzo2 = 3.20;
            Servizio servizio2 = new Servizio();
            servizio2.setNome("Stiratura maglietta");
            servizio2.setTipologia("Stiratura");
            servizio2.setCaratteristiche("Maglietta di cotone");
            servizio2.setPrezzo(prezzo2);

            final double prezzo3 = 4.20;
            Servizio servizio3 = new Servizio();
            servizio3.setNome("Lavaggio jeans");
            servizio3.setTipologia("Lavaggio");
            servizio3.setCaratteristiche("Jeans");
            servizio3.setPrezzo(prezzo3);

            final double prezzo4 = 12.00;
            Servizio servizio4 = new Servizio();
            servizio4.setNome("Lavaggio Piumone");
            servizio4.setTipologia("Lavaggio");
            servizio4.setCaratteristiche("Piumone");
            servizio4.setPrezzo(prezzo3);

            final double prezzo5 = 5.40;
            Servizio servizio5 = new Servizio();
            servizio5.setNome("Stiratura Maglione");
            servizio5.setTipologia("Stiratura");
            servizio5.setCaratteristiche("Maglione");
            servizio5.setPrezzo(prezzo3);
            servizioDAO.saveAll(Arrays.asList(servizio, servizio2, servizio3, servizio4, servizio5));

            final int quantity = 3;
            RigaOrdine rigaOrdine = new RigaOrdine();
            rigaOrdine.setQuantita(1);
            rigaOrdine.setServizio(servizio);

            RigaOrdine rigaOrdine1 = new RigaOrdine();
            rigaOrdine.setQuantita(1);
            rigaOrdine.setServizio(servizio2);

            RigaOrdine rigaOrdine2 = new RigaOrdine();
            rigaOrdine.setQuantita(quantity);
            rigaOrdine.setServizio(servizio3);

            RigaOrdine rigaOrdine3 = new RigaOrdine();
            rigaOrdine.setQuantita(2);
            rigaOrdine.setServizio(servizio5);

            RigaOrdine rigaOrdine4 = new RigaOrdine();
            rigaOrdine1.setQuantita(1);
            rigaOrdine1.setServizio(servizio2);

            final int year = 2022;
            final int month1 = 2;
            final int month2 = 1;
            final int day1 = 3;
            final int day2 = 20;
            final int day3 = 21;
            final int day4 = 11;
            LocalDate tmpdate = LocalDate.of(year, month1, day1);
            Ordine ordine1 = new Ordine(tmpdate, "In sede", "Macchiato");
            ordine1.setCliente(cliente3);
            ordine1.setSede(sede2);

            LocalDate tmpdate1 = LocalDate.of(year, month2, day2);
            Ordine ordine2 = new Ordine(tmpdate1, "Consegna", "Consegnato");
            ordine2.setCliente(cliente3);

            LocalDate tmpdate2 = LocalDate.of(year, month2, day3);
            Ordine ordine3 = new Ordine(tmpdate1, "Consegna", "Imbustato");
            ordine3.setCliente(cliente1);

            LocalDate tmpdate3 = LocalDate.of(year, month2, day4);
            Ordine ordine4 = new Ordine(tmpdate1, "In sede", "Imbustato");
            ordine4.setCliente(cliente4);
            ordine4.setSede(sede1);


            rigaOrdine.setOrdine(ordine1);
            rigaOrdine1.setOrdine(ordine1);
            rigaOrdine2.setOrdine(ordine2);
            rigaOrdine3.setOrdine(ordine3);
            rigaOrdine4.setOrdine(ordine4);

            ordineDAO.saveAll(Arrays.asList(ordine1, ordine2, ordine3, ordine4));
            rigaOrdineDAO.saveAll(Arrays.asList(rigaOrdine, rigaOrdine1, rigaOrdine2, rigaOrdine3, rigaOrdine4));

            ArrayList<RigaOrdine> righe = (ArrayList<RigaOrdine>) rigaOrdineDAO.findAllByOrdine(ordine1);
            for (RigaOrdine r : righe) {
                ordine1.getRigheOrdine().add(r);
            }
            ordineDAO.save(ordine1);

            righe = (ArrayList<RigaOrdine>) rigaOrdineDAO.findAllByOrdine(ordine2);
            for (RigaOrdine r : righe) {
                ordine1.getRigheOrdine().add(r);
            }
            ordineDAO.save(ordine2);

            righe = (ArrayList<RigaOrdine>) rigaOrdineDAO.findAllByOrdine(ordine3);
            for (RigaOrdine r : righe) {
                ordine1.getRigheOrdine().add(r);
            }
            ordineDAO.save(ordine3);

            righe = (ArrayList<RigaOrdine>) rigaOrdineDAO.findAllByOrdine(ordine4);
            for (RigaOrdine r : righe) {
                ordine1.getRigheOrdine().add(r);
            }
            ordineDAO.save(ordine4);

            Macchinario macchinario = new Macchinario();
            macchinario.setMatricola("AB1234");
            macchinario.setDenominazione("MacchinaX");
            macchinario.setCaratteristiche("Bella");
            macchinario.setCostruttore("Whirlpool");
            macchinario.setManutentore("X");
            macchinario.setTelefonoManutenzione("3387610971");
            macchinario.setStato("In funzione");
            macchinario.setSede(sede1);

            Macchinario macchinario2 = new Macchinario();
            macchinario2.setMatricola("CD3456");
            macchinario2.setDenominazione("MacchinaY");
            macchinario2.setCaratteristiche("Bella");
            macchinario2.setCostruttore("Whirlpool");
            macchinario2.setManutentore("X");
            macchinario2.setTelefonoManutenzione("3387610971");
            macchinario2.setStato("Inattivo");
            macchinario2.setSede(sede2);


            Operatore operatore = new Operatore();
            operatore.setNome("Luigi");
            operatore.setCognome("Di maio");
            operatore.setUsername(operatore.generateString());
            operatore.setPassword(operatore.generateString());

            Operatore operatore2 = new Operatore();
            operatore2.setNome("Matteo");
            operatore2.setCognome("Renzi");
            operatore2.setUsername("admin");
            operatore2.setPassword("admin");


            Etichetta etichetta = new Etichetta();
            etichetta.setOrdine(ordine1);
            etichetta.setSede(sede1);


            macchinarioDAO.saveAll(Arrays.asList(macchinario, macchinario2));

            operatoreDAO.saveAll(Arrays.asList(operatore, operatore2));
            etichettaDAO.save(etichetta);

            System.out.println(clienteDAO.findAll());
            System.out.println(operatoreDAO.findAll());
            System.out.println(ordineDAO.findAll());
            System.out.println(rigaOrdineDAO.findAll());

        };
    }
}
