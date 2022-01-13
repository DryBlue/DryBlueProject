
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DryBlueApplication {

	public static void main(String[] args) {
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
	)
	{
		return args -> {

			Sede sede1 = new Sede("Ariano Irpino, via Cardito, 52");
			Sede sede2 = new Sede("Ariano Irpino, corso Vittorio Emanuele, 250");

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

			RigaOrdine rigaOrdine = new RigaOrdine();
			rigaOrdine.setQuantita(1);
			rigaOrdine.setServizio(servizio);

			RigaOrdine rigaOrdine1 = new RigaOrdine();
			rigaOrdine1.setQuantita(1);
			rigaOrdine1.setServizio(servizio2);



			LocalDate tmpdate = LocalDate.of(2022, 2, 3);
			Ordine ordine1 = new Ordine(tmpdate, "ritiro", "Macchiato");
			ordine1.setCliente(cliente3);
			ordine1.setSede(sede1);
			ordine1.setRigheOrdine(new HashSet<RigaOrdine>());
			ordine1.getRigheOrdine().add(rigaOrdine);
			ordine1.getRigheOrdine().add(rigaOrdine1);
			LocalDate tmpdate1 = LocalDate.of(2022,  1, 20);
			Ordine ordine2 = new Ordine(tmpdate1, "ritiro", "Consegnato");
			ordine2.setCliente(cliente3);
			ordine2.setSede(sede1);
			LocalDate tmpdate2 = LocalDate.of(2022,  1, 21);
			Ordine ordine3 = new Ordine(tmpdate1, "ritiro", "Imbustato");
			ordine3.setCliente(cliente1);
			ordine3.setSede(sede1);


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


			sedeDAO.saveAll(Arrays.asList(sede1, sede2));
			clienteDAO.saveAll(Arrays.asList(cliente1, cliente2, cliente3));
			ordineDAO.saveAll(Arrays.asList(ordine1, ordine2, ordine3));
			rigaOrdineDAO.saveAll(Arrays.asList(rigaOrdine,rigaOrdine1));
			macchinarioDAO.save(macchinario);
			servizioDAO.saveAll(Arrays.asList(servizio, servizio2, servizio3));
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
