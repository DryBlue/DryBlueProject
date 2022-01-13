package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import it.unisa.DryBlue.ordini.domain.RigaOrdine;
import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.ordini.services.OrdiniService;
import it.unisa.DryBlue.servizi.services.ServizioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@SessionAttributes("utente")
@RequestMapping("/ordini")
public class OrdiniController {
    private final OrdiniService ordiniService;
    private final ServizioService servizioService;
    private final GestioneClienteService gestioneClienteService;


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

}
