
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
								  final EtichettaDAO etichettaDAO,
								  final PropostaModificaDAO propostaModificaDAO
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
			cliente1.setIndirizzo("Via Polpette al sugo 14");

			Cliente cliente2 = new Cliente();
			cliente2.setNumeroTelefono("34563275671");
			cliente2.setUsername(cliente2.generateString());
			cliente2.setPassword(cliente2.generateString());
			cliente2.setNome("Paolo");
			cliente2.setCognome("Brosio");
			cliente2.setIndirizzo("Via Mazzini 4");

			Cliente cliente3 = new Cliente();
			cliente3.setNumeroTelefono("34733275671");
			cliente3.setUsername("user");
			cliente3.setPassword("user");
			cliente3.setNome("Gianfranco");
			cliente3.setCognome("Bonromeo");
			cliente3.setIndirizzo("Via Polpette al sugo 45");



			Cliente cliente4 = new Cliente();
			cliente4.setNumeroTelefono("3332994445");
			cliente4.setUsername("user1");
			cliente4.setPassword("user1");
			cliente4.setNome("Camilla");
			cliente4.setCognome("Bonomelli");
			cliente4.setIndirizzo("Via Polpette al sugo 45");
			cliente4.setEmail("miriamferrara1397@gmail.com");
			clienteDAO.saveAll(Arrays.asList(cliente1, cliente2, cliente3,cliente4));


			Servizio servizio = new Servizio();
			servizio.setNome("Lavaggio maglia blu");
			servizio.setTipologia("Lavaggio");
			servizio.setCaratteristiche("Maglia blu");
			servizio.setPrezzo(10.20);

			Servizio servizio2 = new Servizio();
			servizio2.setNome("Stiratura maglia blu");
			servizio2.setTipologia("Stiratura");
			servizio2.setCaratteristiche("Maglia blu");
			servizio2.setPrezzo(5.20);

			Servizio servizio3 = new Servizio();
			servizio3.setNome("Lavaggio jeans");
			servizio3.setTipologia("Lavaggio");
			servizio3.setCaratteristiche("Jeans");
			servizio3.setPrezzo(10.20);
			servizioDAO.saveAll(Arrays.asList(servizio, servizio2, servizio3));

			RigaOrdine rigaOrdine = new RigaOrdine();
			rigaOrdine.setQuantita(1);
			rigaOrdine.setServizio(servizio);

			RigaOrdine rigaOrdine1 = new RigaOrdine();
			rigaOrdine1.setQuantita(1);
			rigaOrdine1.setServizio(servizio2);


			LocalDate tmpdate = LocalDate.of(2022, 2, 3);
			Ordine ordine1 = new Ordine(tmpdate, "In sede", "Macchiato");
			ordine1.setCliente(cliente3);
			ordine1.setSede(sede1);

			LocalDate tmpdate1 = LocalDate.of(2022,  1, 20);
			Ordine ordine2 = new Ordine(tmpdate1, "Consegna", "Consegnato");
			ordine2.setCliente(cliente3);

			LocalDate tmpdate2 = LocalDate.of(2022,  1, 21);
			Ordine ordine3 = new Ordine(tmpdate1, "Consegna", "Imbustato");
			ordine3.setCliente(cliente1);

			LocalDate tmpdate3 = LocalDate.of(2022, 1, 11);
			Ordine ordine4 = new Ordine(tmpdate1, "ritiro", "Imbustato");
			ordine4.setCliente(cliente4);
			ordine4.setSede(sede1);


			rigaOrdine.setOrdine(ordine1);
			rigaOrdine1.setOrdine(ordine1);

			ordineDAO.saveAll(Arrays.asList(ordine1, ordine2, ordine3,ordine4));
			rigaOrdineDAO.saveAll(Arrays.asList(rigaOrdine, rigaOrdine1));

			ArrayList<RigaOrdine> righe = (ArrayList<RigaOrdine>) rigaOrdineDAO.findAllByOrdine(ordine1);
			for (RigaOrdine r : righe) {
				ordine1.getRigheOrdine().add(r);
			}
			ordineDAO.save(ordine1);

			Macchinario macchinario = new Macchinario();
			macchinario.setMatricola("AB1234");
			macchinario.setDenominazione("MacchinaX");
			macchinario.setCaratteristiche("Bella");
			macchinario.setCostruttore("Whirlpool");
			macchinario.setManutentore("X");
			macchinario.setTelefonoManutenzione("3387610971");
			macchinario.setStato("In funzione");
			macchinario.setSede(sede1);


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


			PropostaModifica propostaModifica = new PropostaModifica();
			propostaModifica.setDataProposta(tmpdate);
			propostaModifica.setCliente(cliente1);
			propostaModifica.setOrdine(ordine1);
			propostaModifica.setStato("Sospeso");
			propostaModifica.setSede(sede1);

			macchinarioDAO.save(macchinario);

			operatoreDAO.saveAll(Arrays.asList(operatore, operatore2));
			etichettaDAO.save(etichetta);
			propostaModificaDAO.save(propostaModifica);


			System.out.println(clienteDAO.findAll());
			System.out.println(operatoreDAO.findAll());
			System.out.println(ordineDAO.findAll());
			System.out.println(rigaOrdineDAO.findAll());

		};
	}
}
