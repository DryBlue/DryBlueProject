package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import it.unisa.DryBlue.ordini.dao.OrdineDAO;
import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.ordini.domain.RigaOrdine;
import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.ordini.services.OrdiniService;
import it.unisa.DryBlue.ordini.util.MailSingletonSender;
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
import java.util.List;
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
    private final OrdineDAO ordineDAO;


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
                                  final @RequestParam("tipologiaRitiro") String tipologiaRitiro,
                                  final @RequestParam("sede") Sede sede,
                                  final @RequestParam("dataConsegnaDesiderata")LocalDate dataConsegnaDesiderata,
                                  final @RequestParam("sedeDesiderata") Integer sedeDesiderata,
                                  final @RequestParam("note") String note) {
        ordiniService.creazioneOrdine(rigaOrdine, quantita, cliente, tipologiaRitiro,
                sede, dataConsegnaDesiderata, sedeDesiderata, note);
        model.getAttribute("utente");

        return "/LoggedHomepage";

    }

    @GetMapping("/ListaOrdini")
    private String listaOrdini(final @RequestParam(value = "filter", defaultValue = "Attivi")  String filter,
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
    public String Modifica(Model m,
                           @RequestParam("codiceOrdine3") String id_ordine) {

        m.addAttribute("id", id_ordine);
        m.getAttribute("utente");
        return "/ordini/ModificaOrdineOperatore";

    }

    @PostMapping("/ListaOrdini/ModificaOrdine")
    public String ModificaOrdine(Model m,
                                 @RequestParam("stato") String stato,
                                 @RequestParam("idOrdine") Integer id_ordine) {

        ordiniService.modificaOrdine(null, null, stato, id_ordine);
        m.getAttribute("utente");
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        String email = ordine.getCliente().getEmail();

        if(email != null) {
            sender.sendEmail(ordine, email);
        }
        return listaOrdini("Attivi", m);
    }



    @PostMapping("/StampaEtichetta")
    public void exportToPDF(HttpServletResponse response, Model m,  @RequestParam("codiceOrdine1") Integer id_ordine) throws Exception {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Etichetta_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        m.addAttribute("id", id_ordine);
        Ordine ordine = ordineDAO.findById(id_ordine).get();
        String nome = ordine.getCliente().getNome();
        String cognome= ordine.getCliente().getCognome();
        String indirizzo = ordine.getCliente().getIndirizzo();
        ordiniService.stampaEtichetta(ordine);

        PDFExport e = new  PDFExport();
        e.export(response, nome,cognome,indirizzo);
    }

}
