package it.unisa.DryBlue.ordini.services;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.dao.*;
import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.ordini.domain.PropostaModifica;
import it.unisa.DryBlue.ordini.domain.RigaOrdine;
import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.servizi.domain.Servizio;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@SpringBootTest
public class OrdiniServiceImplIT {

    private OrdiniService ordiniService;

    @Autowired
    private OrdineDAO ordineDAO;
    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private PropostaModificaDAO propostaModificaDAO;
    @Autowired
    private EtichettaDAO etichettaDAO;
    @Autowired
    private SedeDAO sedeDAO;
    @Autowired
    private RigaOrdineDAO rigaOrdineDAO;

    private Sede sede1, sede2;
    private Ordine ordine1;
    private Cliente cliente, cliente1;
    private Set<RigaOrdine> rigaOrdine;
    private RigaOrdine riga;
    private Servizio servizio;
    private PropostaModifica propostaModifica;


    @BeforeEach
    public void init() {
        ordiniService = new OrdiniServiceImpl(ordineDAO, propostaModificaDAO, etichettaDAO, sedeDAO,
                clienteDAO, rigaOrdineDAO);

        final int y1 = 2022;
        final int m1 = 03;
        final int d1 = 02;
        propostaModifica = new PropostaModifica("In attesa");
        propostaModifica.setCliente(cliente1);
        propostaModifica.setOrdine(ordine1);
        propostaModifica.setSede(sede1);
        propostaModifica.setDataProposta(LocalDate.of(y1, m1, d1));


        final double p = 20.0;
        servizio = new Servizio("nome", "tipo", "caratteristica", p);

        sede1 = new Sede("Ariano Irpino, via Cardito, 52");


        cliente = new Cliente("user", "user", "via Rossi 12", "Maria", "Rossi");
        cliente.setNumeroTelefono("333444555");

        cliente1 = new Cliente("user", "user", "via Rossi 12", "Maria", "Rossi");
        cliente1.setNumeroTelefono("333444777");
        cliente1.setPropostaModifica(propostaModifica);

        rigaOrdine = new HashSet<>();
        riga = new RigaOrdine(2);
        riga.setServizio(servizio);


        final int y = 2022;
        final int m = 03;
        final int d = 02;

        LocalDate data1 = LocalDate.of(y, m, d);
        ordine1 = new Ordine(data1, "In sede", "Macchiato");
        ordine1.setCliente(cliente);
        ordine1.setSede(sede1);
        ordine1.setPropostaModifica(propostaModifica);
        ordine1.setRigheOrdine(rigaOrdine);

        riga.setId(ordine1.getId());
        riga.setOrdine(ordine1);
        rigaOrdine.add(riga);

        List<Ordine> s = (List<Ordine>) ordineDAO.findAll();

        for (Ordine e : s) {
            System.out.println(e);
        }
    }
/*
    @Test
    public void creazioneOrdine() {

        final int y = 2022;
        final int m = 03;
        final int d = 02;

        LocalDate data1 = LocalDate.of(y, m, d);
        Ordine crea =ordineDAO.save(ordine1);
        ordiniService.creazioneOrdine(rigaOrdine, ordine1.getCliente().getNumeroTelefono(), "In sede", sede1.getIndirizzo(), data1, null);


        assertEquals(ordineDAO.findById(ordine1.getId()), ordineDAO.findById(crea.getId()));
*/


}
