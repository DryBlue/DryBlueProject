package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import it.unisa.DryBlue.ordini.dao.OrdineDAO;
import it.unisa.DryBlue.ordini.dao.PropostaModificaDAO;
import it.unisa.DryBlue.ordini.dao.RigaOrdineDAO;
import it.unisa.DryBlue.ordini.dao.SedeDAO;
import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.ordini.domain.PropostaModifica;
import it.unisa.DryBlue.ordini.domain.RigaOrdine;
import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.ordini.services.OrdiniService;
import it.unisa.DryBlue.ordini.util.MailSingletonSender;
import it.unisa.DryBlue.ordini.util.MailSingletonSenderProposta;
import it.unisa.DryBlue.ordini.util.PDFExport;
import it.unisa.DryBlue.servizi.services.ServizioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@SessionAttributes("utente")
@RequestMapping("/ordini")
public class OrdiniController {
    private final OrdiniService ordiniService;
    private final ServizioService servizioService;
    private final GestioneClienteService gestioneClienteService;
    @Autowired
    private final MailSingletonSender sender;
    @Autowired
    private final MailSingletonSenderProposta senderProposta;
    private final OrdineDAO ordineDAO;
    private final SedeDAO sedeDAO;
    private final RigaOrdineDAO rigaOrdineDAO;
    private final PropostaModificaDAO propostaModificaDAO;
    private final ClienteDAO clienteDAO;

    @GetMapping("/form")
    private String form(final Model model) {
        model.addAttribute("servizi", servizioService.findServizi());
        model.addAttribute("sedi", ordiniService.visualizzaSedi());
        model.addAttribute("clienti", gestioneClienteService.findTuttiIClienti());
        model.getAttribute("utente");
        return "ordini/aggiuntaOrdine";
    }

    @PostMapping("/aggiuntaOrdine")
    private String aggiuntaOrdine(final Model model,
                                  final @RequestParam("rigaOrdine") Set<RigaOrdine> rigaOrdine,
                                  final @RequestParam("quantita") Integer quantita,
                                  final @RequestParam("cliente") Cliente cliente,
                                  final @RequestParam("ritiro") String tipologiaRitiro,
                                  final @RequestParam("sede") Sede sede,
                                  final @RequestParam("date") LocalDate dataConsegnaDesiderata,
                                  final @RequestParam("note") String note) {
        ordiniService.creazioneOrdine(rigaOrdine, quantita, cliente, tipologiaRitiro, sede, dataConsegnaDesiderata, note);
        model.getAttribute("utente");

        return "/LoggedHomepage";

    }

    @GetMapping("/ListaOrdini")
    private String listaOrdini(final @RequestParam(value = "filter", defaultValue = "Attivi") String filter,
                               final Model model) {
        Utente u = (Utente) model.getAttribute("utente");
        if (u.getRuolo().getName().equals("OPERATORE")) {
            if (filter.equals("Totali")) {
                model.addAttribute("ordini", ordiniService.visualizzaOrdiniTotali());
                return "ordini/ListaOrdini";
            } else if (filter.equals("Attivi")) {
                model.addAttribute("ordini", ordiniService.visualizzaOrdiniFiltroOperatore(filter));
                return "ordini/ListaOrdini";
            } else {
                model.addAttribute("ordini", ordiniService.visualizzaOrdiniFiltroOperatore(filter));
                return "ordini/ListaOrdini";
            }
        } else {
            if (filter.equals("Attivi")) {
                model.addAttribute("ordini", ordiniService.visualizzaOrdiniFiltroUtente(filter, u.getCellulare()));
                return "ordini/ListaOrdini";
            } else {
                model.addAttribute("ordini", ordiniService.visualizzaOrdiniFiltroUtente(filter, u.getCellulare()));
                return "ordini/ListaOrdini";
            }
        }
    }

    @PostMapping("/dettaglioOrdine")
    private String dettaglioOrdine(final @RequestParam("codiceOrdine") int idOrdine, final Model model) {
        model.getAttribute("utente");
        model.addAttribute("dOrdine", ordiniService.findById(idOrdine).get());
        return "ordini/DettaglioOrdine";
    }

    @PostMapping("/ListaOrdini/Modifica")
    public String Modifica(final Model m,
                           final @RequestParam("codiceOrdine3") String id_ordine) {

        m.addAttribute("id", id_ordine);
        m.addAttribute("ordine", ordineDAO.findById(Integer.valueOf(id_ordine)).get());
        m.getAttribute("utente");
        return "/ordini/ModificaOrdineOperatore";

    }

    @PostMapping("/ListaOrdini/ModificaOrdine")
    public String ModificaOrdine(final Model m,
                                 final @RequestParam("stato") String stato,
                                 final @RequestParam("idOrdine") Integer id_ordine) {

        ordiniService.modificaOrdine(null, null, stato, id_ordine);
        m.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        String email = ordine.getCliente().getEmail();

        if (email != null) {
            sender.sendEmail(ordine, email);
        }
        return listaOrdini("Attivi", m);
    }

    @PostMapping("/ListaOrdini/ModificaData")
    public String ModificaData(final Model m,
                               final @RequestParam("data") String data,
                               final @RequestParam("idOrdine") Integer id_ordine) {
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        LocalDate date = LocalDate.parse(data);
        ordine.setDataConsegnaDesiderata(date);
        ordineDAO.save(ordine);
        return listaOrdini("Attivi", m);
    }

    @PostMapping("/ListaOrdini/ModificaSede")
    public String ModificaOrdine(final Model m, final @RequestParam("idOrdine") Integer id_ordine) {
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        if (ordine.getSede().getIndirizzo().equals("Ariano Irpino, via Cardito, 52")) {
            ordine.setSede(sedeDAO.findByIndirizzo("Ariano Irpino, corso Vittorio Emanuele, 250"));
        } else if (ordine.getSede().getIndirizzo().equals("Ariano Irpino, corso Vittorio Emanuele, 250")) {
            ordine.setSede(sedeDAO.findByIndirizzo("Ariano Irpino, via Cardito, 52"));
        }
        ordineDAO.save(ordine);
        return listaOrdini("Attivi", m);
    }


    @PostMapping("/StampaEtichetta")
    public void exportToPDF(final HttpServletResponse response, final Model m, final @RequestParam("codiceOrdine1") Integer id_ordine) throws Exception {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Etichetta_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        m.addAttribute("id", id_ordine);
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        String nome = ordine.getCliente().getNome();
        String cognome = ordine.getCliente().getCognome();
        String indirizzo = ordine.getCliente().getIndirizzo();
        ordiniService.stampaEtichetta(ordine);

        PDFExport e = new PDFExport();
        e.export(response, nome, cognome, indirizzo, id_ordine);
    }

    @PostMapping("/propostaModifica")
    public String propostaModifica(final Model model,
                                   final @RequestParam("sedeNuova") String sede,
                                   final @RequestParam("dataProposta") String data,
                                   final @RequestParam("Ordine") Integer id) {
        LocalDate date = LocalDate.parse(data);
        Ordine ordine = ordineDAO.findById(id).get();
        ordiniService.propostaModifica(date, sede, ordine);

        PropostaModifica proposta = ordine.getPropostaModifica();
        proposta.setDataProposta(date);
        propostaModificaDAO.save(proposta);
        return listaOrdini("Attivi", model);
    }

    @GetMapping("/propostaModifica")
    public String propostaPage(final Model model,
                               final @RequestParam("codiceOrdine4") Integer id) {
        model.addAttribute("ordine", ordiniService.findById(id).get());
        model.addAttribute("sedi", ordiniService.visualizzaSedi());
        return "/ordini/propostaModifica";
    }


    @PostMapping("/ValutazioneProposta")
    public String ValutazioneProposta(final Model m, final @RequestParam("codiceOrdine2") Integer id_ordine) {
        m.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        Integer idProp = ordine.getPropostaModifica().getId();
        PropostaModifica pr = propostaModificaDAO.findById(idProp).get();
        String data = pr.getDataProposta().toString();
        String telefono = ordine.getCliente().getNumeroTelefono();
        Cliente cliente = clienteDAO.findByNumeroTelefono(telefono);

        m.addAttribute("ordin", ordine);
        m.addAttribute("cliente", cliente);
        m.addAttribute("dataP", pr);
        return "/ordini/ValutazionePropostaOperatore";

    }


    @PostMapping("/ValutazioneAccetta")
    public String ValutazioneAccetta(final Model m,
                                     final @RequestParam("accetta") Integer accetta) {
        m.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(accetta).get();
        String email = ordine.getCliente().getEmail();

        String scelta = "ACCETTATA";
        if (email != null) {
            senderProposta.sendEmail(email, scelta);
        }
        //return "/ordini/ModificaOrdineOperatore";
        return listaOrdini("Attivi", m);
    }

    @PostMapping("/ValutazioneRifiuta")
    public String ValutazioneRifiuta(final Model m, final @RequestParam("rifiuta") Integer rifiuta) {
        m.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(rifiuta).get();
        String email = ordine.getCliente().getEmail();

        String scelta = "RIFIUTATA";
        if (email != null) {
            senderProposta.sendEmail(email, scelta);
        }

        return listaOrdini("Attivi", m);

    }


}
