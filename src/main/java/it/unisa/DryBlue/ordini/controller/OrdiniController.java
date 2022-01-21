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
import it.unisa.DryBlue.ordini.services.OrdiniService;
import it.unisa.DryBlue.ordini.util.MailSingletonSender;
import it.unisa.DryBlue.ordini.util.MailSingletonSenderProposta;
import it.unisa.DryBlue.ordini.util.PDFExport;
import it.unisa.DryBlue.servizi.domain.Servizio;
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
import java.util.HashSet;
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
    private Set<RigaOrdine> righe = new HashSet<>();


    @GetMapping("/form")
    private String form(final Model model) {
        model.addAttribute("servizi", servizioService.findServizi());
        model.addAttribute("sedi", ordiniService.visualizzaSedi());
        model.addAttribute("clienti", gestioneClienteService.findTuttiIClienti());
        model.getAttribute("utente");
        model.getAttribute("righe");
        return "ordini/aggiuntaOrdine";
    }

    /**
     * Implementa la funzionalità che permette
     * di aggiungere una nuova riga all'ordine che
     * si sta effettuando.
     *
     * @param model il Model
     * @param idServizio l'identificativo del Servizio
     * @param quantita la quantità di capi di abbigliamento dello stesso tipo
     * @return la pagina dove è visualizzato
     */
    @PostMapping("/aggiuntaRiga")
    private String aggiuntaRiga(final Model model,
                                final @RequestParam("idServizio") Integer idServizio,
                                final @RequestParam("quantity") Integer quantita) {
        model.getAttribute("utente");
        Servizio servizio = servizioService.findServizioById(idServizio);
        System.out.println(servizio.toString());
        RigaOrdine riga = new RigaOrdine(quantita);
        riga.setServizio(servizio);
        riga.setOrdine(null);
        ordiniService.creaRigaOrdine(riga);
        righe.add(riga);
        model.addAttribute("righe", righe);
        model.addAttribute("servizi", servizioService.findServizi());
        model.addAttribute("sedi", ordiniService.visualizzaSedi());
        model.addAttribute("clienti", gestioneClienteService.findTuttiIClienti());
        model.getAttribute("utente");
        return "ordini/aggiuntaOrdine";
    }

    /**
     * Implementa la funzionalità che permette
     * di creare un nuovo ordine che si è compilato.
     *
     * @param model il Model
     * @param cliente il cliente
     * @param tipologiaRitiro la tipologia di ritiro
     * @param sede la sede di ritiro
     * @param dataConsegnaDesiderata la data di consegna desiderata per l'ordine
     * @param note eventuali note aggiuntive all'ordine
     * @return la pagina di homepage
     */
    @PostMapping("/aggiuntaOrdine")
    private String aggiuntaOrdine(final Model model,
                                  final @RequestParam("cliente") String cliente,
                                  final @RequestParam("ritiro") String tipologiaRitiro,
                                  final @RequestParam("sedeDesiderata") String sede,
                                  final @RequestParam("date") String dataConsegnaDesiderata,
                                  final @RequestParam("note") String note) {
        LocalDate date = LocalDate.parse(dataConsegnaDesiderata);
        ordiniService.creazioneOrdine(righe, cliente, tipologiaRitiro, sede, date, note);
        model.getAttribute("utente");
        righe.clear();
        return "/LoggedHomepage";

    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la lista ordini controllando se
     * si è un operatore o un cliente
     * ed implementando di conseguenza i diversi filtri di ricerca.
     *
     * @param model il Model
     * @param filter il filtro di ricerca che si è selezionato
     * @return la pagina della lista ordini
     */
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

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la pagina di dettaglio di un ordine.
     *
     * @param model il Model
     * @param idOrdine l'identificativo dell'ordine
     * @return la pagina di dettaglio dell'ordine
     */
    @PostMapping("/dettaglioOrdine")
    private String dettaglioOrdine(final @RequestParam("codiceOrdine") int idOrdine,
                                   final Model model) {
        model.getAttribute("utente");
        model.addAttribute("dOrdine", ordiniService.findById(idOrdine).get());
        return "ordini/DettaglioOrdine";
    }

    /**
     * Implementa la funzionalità che permette
     * di modificare un ordine da parte dell'operatore.
     *
     * @param model il Model
     * @param id_ordine l'identificativo dell'ordine
     * @return la pagina di modifica da parte dell'operatore di un ordine
     */
    @PostMapping("/ModificaOrdineOperatore")
    public String Modifica(final Model model,
                           final @RequestParam("codiceOrdine3") String id_ordine) {

        model.addAttribute("id", id_ordine);
        model.addAttribute("ordine", ordineDAO.findById(Integer.valueOf(id_ordine)).get());
        model.getAttribute("utente");
        return "/ordini/ModificaOrdineOperatore";

    }

    /**
     * Implementa la modifica dell'ordine effettuata dall'operatore.
     *
     * @param model il Model
     * @param id_ordine l'identificativo dell'ordine
     * @param stato lo stato in cui si trova l'ordine ("Macchiato", "Pronto", "Imbustato")
     * @return il metodo che reindirizza alla pagina della lista ordini
     */
    @PostMapping("/ListaOrdini/ModificaOrdine")
    public String ModificaStato(final Model model,
                                 final @RequestParam("stato") String stato,
                                 final @RequestParam("idOrdine") Integer id_ordine) {

        ordiniService.modificaOrdine(null, null, stato, id_ordine);
        model.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        String email = ordine.getCliente().getEmail();

        if (email != null) {
            sender.sendEmail(ordine, email);
        }
        return listaOrdini("Attivi", model);
    }

    /**
     * Implementa la funzionalità che permette
     * di modificare la data di consegna di un ordine.
     *
     * @param model il Model
     * @param data la nuova data di consegna
     * @param id_ordine l'identificativo dell'ordine
     * @return la pagina di visualizzazione della lista ordini
     */
    @PostMapping("/ListaOrdini/ModificaData")
    public String ModificaData(final Model model,
                               final @RequestParam("data") String data,
                               final @RequestParam("idOrdine") Integer id_ordine) {
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        LocalDate date = LocalDate.parse(data);
        ordine.setDataConsegnaDesiderata(date);
        ordineDAO.save(ordine);
        return listaOrdini("Attivi", model);
    }

    /**
     * Implementa la funzionalità che permette
     * di modificare la sede di consegna di un ordine.
     *
     * @param model il Model
     * @param id_ordine l'identificativo dell'ordine
     * @return la pagina di visualizzazione della lista ordini
     */
    @PostMapping("/ListaOrdini/ModificaSede")
    public String ModificaOrdine(final Model model,
                                 final @RequestParam("idOrdine") Integer id_ordine) {
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        if (ordine.getSede().getIndirizzo().equals("Ariano Irpino, via Cardito, 52")) {
            ordine.setSede(sedeDAO.findByIndirizzo("Ariano Irpino, corso Vittorio Emanuele, 250"));
        } else if (ordine.getSede().getIndirizzo().equals("Ariano Irpino, corso Vittorio Emanuele, 250")) {
            ordine.setSede(sedeDAO.findByIndirizzo("Ariano Irpino, via Cardito, 52"));
        }
        ordineDAO.save(ordine);
        return listaOrdini("Attivi", model);
    }

    /**
     * Implementa la funzionalità che permette
     * di stampare l'etichetta di un determinato ordine.
     *
     * @param model il Model
     * @param response parametro utilizzato per spedire una risposta
     * @param id_ordine l'identificativo dell'ordine
     * @return la pagina di visualizzazione della lista ordini
     */
    @PostMapping("/StampaEtichetta")
    public void exportToPDF(final HttpServletResponse response,
                            final Model model,
                            final @RequestParam("codiceOrdine1") Integer id_ordine) throws Exception {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Etichetta_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        model.addAttribute("id", id_ordine);
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        String nome = ordine.getCliente().getNome();
        String cognome = ordine.getCliente().getCognome();
        String indirizzo = ordine.getCliente().getIndirizzo();
        ordiniService.stampaEtichetta(ordine);

        PDFExport e = new PDFExport();
        e.export(response, nome, cognome, indirizzo, id_ordine);
    }

    /**
     * Implementa la funzionalità che permette al cliente
     * di proporre una modifica di un ordine all'operatore .
     *
     * @param model il Model
     * @param data la nuova data di consegna
     * @param id l'identificativo dell'ordine
     * @return la pagina di visualizzazione della lista ordini
     */
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

    /**
     * Implementa la funzionalità che reindirizza il cliente
     * alla pagina di proposta modifica di un ordine.
     *
     * @param model il Model
     * @param id l'identificativo dell'ordine
     * @return la pagina di proposta modifica dell'ordine
     */
    @GetMapping("/propostaModifica")
    public String propostaPage(final Model model,
                               final @RequestParam("codiceOrdine4") Integer id) {
        model.addAttribute("ordine", ordiniService.findById(id).get());
        model.addAttribute("sedi", ordiniService.visualizzaSedi());
        return "/ordini/propostaModifica";
    }

    /**
     * Implementa la funzionalità che reindirizza l'operatore
     * alla pagina di valutazione della proposta di modifica di un ordine
     * da parte del cliente.
     *
     * @param model il Model
     * @param id_ordine l'identificativo dell'ordine
     * @return la pagina di valutazione della proposta di modifica
     */
    @PostMapping("/ValutazioneProposta")
    public String ValutazioneProposta(final Model model,
                                      final @RequestParam("codiceOrdine2") Integer id_ordine) {
        model.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        Integer idProp = ordine.getPropostaModifica().getId();
        PropostaModifica pr = propostaModificaDAO.findById(idProp).get();
        String data = pr.getDataProposta().toString();
        String telefono = ordine.getCliente().getNumeroTelefono();
        Cliente cliente = clienteDAO.findByNumeroTelefono(telefono);

        model.addAttribute("ordin", ordine);
        model.addAttribute("cliente", cliente);
        model.addAttribute("dataP", pr);
        return "/ordini/ValutazionePropostaOperatore";

    }

    /**
     * Implementa la funzionalità che permette all'operatore
     * di accettare la proposta di modifica di un ordine
     * da parte di un cliente.
     *
     * @param model il Model
     * @param accetta l'identificativo dell'ordine
     * @return la pagina di visualizzazione della lista ordini
     */
    @PostMapping("/ValutazioneAccetta")
    public String ValutazioneAccetta(final Model model,
                                     final @RequestParam("accetta") Integer accetta) {
        model.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(accetta).get();
        String email = ordine.getCliente().getEmail();

        Integer idProp = ordine.getPropostaModifica().getId();
        PropostaModifica pr = propostaModificaDAO.findById(idProp).get();
        LocalDate data = pr.getDataProposta();
        System.out.println("data " + data);

        ordine.setDataConsegnaDesiderata(data);
        pr.setStato("Conclusa");

        String scelta = "ACCETTATA";
        if (email != null) {
            senderProposta.sendEmail(email, scelta);
        }
        propostaModificaDAO.save(pr);
        ordineDAO.save(ordine);
        return listaOrdini("Attivi", model);
    }

    /**
     * Implementa la funzionalità che permette all'operatore
     * di rifiutare la proposta di modifica di un ordine
     * da parte di un cliente.
     *
     * @param model il Model
     * @param rifiuta l'identificativo dell'ordine
     * @return la pagina di visualizzazione della lista ordini
     */
    @PostMapping("/ValutazioneRifiuta")
    public String ValutazioneRifiuta(final Model model,
                                     final @RequestParam("rifiuta") Integer rifiuta) {
        model.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(rifiuta).get();
        String email = ordine.getCliente().getEmail();

        Integer idProp = ordine.getPropostaModifica().getId();
        PropostaModifica pr = propostaModificaDAO.findById(idProp).get();

        pr.setStato("Conclusa");

        String scelta = "RIFIUTATA";
        if (email != null) {
            senderProposta.sendEmail(email, scelta);
        }
        propostaModificaDAO.save(pr);
        return listaOrdini("Attivi", model);

    }


}
