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

			Sede sede1=new Sede("Via Roma 3");
			Sede sede2=new Sede("Via Delle Industrie 18");

			Cliente cliente1=new Cliente();
			cliente1.setNumeroTelefono("3456327821");
			cliente1.setUsername(cliente1.generateString());
			cliente1.setPassword(cliente1.generateString());
			cliente1.setNome("Mario");
			cliente1.setCognome("Rossi");
			cliente1.setIndirizzo("Via Polpette al sugo 14");

			Cliente cliente2=new Cliente();
			cliente2.setNumeroTelefono("34563275671");
			cliente2.setUsername(cliente2.generateString());
			cliente2.setPassword(cliente2.generateString());
			cliente2.setNome("Paolo");
			cliente2.setCognome("Brosio");
			cliente2.setIndirizzo("Via Mazzini 4");

			LocalDate tmpdate=LocalDate.of(2022,2,3);
			Ordine ordine1=new Ordine(tmpdate,"ritiro","macchiato");
			ordine1.setCliente(cliente1);
			ordine1.setSede(sede1);

			Macchinario macchinario=new Macchinario();
			macchinario.setMatricola("AB1234");
			macchinario.setDenominazione("MacchinaX");
			macchinario.setCaratteristiche("Bella");
			macchinario.setCostruttore("Whirlpool");
			macchinario.setManutentore("X");
			macchinario.setTelefonoManutenzione("3387610971");
			macchinario.setStato("In funzione");
			macchinario.setSede(sede1);


			Servizio servizio=new Servizio();
			servizio.setNome("MagliaLavaggio");
			servizio.setTipologia("Lavaggio");
			servizio.setCaratteristiche("Maglia blu");
			servizio.setPrezzo(10.20);

			Operatore operatore=new Operatore();
			operatore.setNome("Luigi");
			operatore.setCognome("Di maio");
			operatore.setUsername(operatore.generateString());
			operatore.setPassword(operatore.generateString());

			RigaOrdine rigaOrdine=new RigaOrdine();
			rigaOrdine.setOrdine(ordine1);
			rigaOrdine.setQuantita(1);
			rigaOrdine.setServizio(servizio);

			Etichetta etichetta=new Etichetta();
			etichetta.setOrdine(ordine1);
			etichetta.setSede(sede1);

			PropostaModifica propostaModifica=new PropostaModifica();
			propostaModifica.setDataProposta(tmpdate);
			propostaModifica.setCliente(cliente1);
			propostaModifica.setOrdine(ordine1);
			propostaModifica.setStato("Sospeso");
			propostaModifica.setSede(sede1);


			sedeDAO.saveAll(Arrays.asList(sede1,sede2));
			clienteDAO.saveAll(Arrays.asList(cliente1,cliente2));
			ordineDAO.save(ordine1);
			macchinarioDAO.save(macchinario);
			servizioDAO.save(servizio);
			operatoreDAO.save(operatore);
			rigaOrdineDAO.save(rigaOrdine);
			etichettaDAO.save(etichetta);
			propostaModificaDAO.save(propostaModifica);

			System.out.println(clienteDAO.findAll());



		};
	 }
	}
