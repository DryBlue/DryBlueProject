package it.unisa.DryBlue.ordini.controller;

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
    private String form(final Model model){
        model.addAttribute("servizi", servizioService.findServizi());
        model.addAttribute("sedi", ordiniService.visualizzaSedi());
        model.addAttribute("clienti", gestioneClienteService.findTuttiIClienti());
        model.getAttribute("utente");
        return "ordini/aggiuntaOrdine";
    }

    @PostMapping("/aggiuntaOrdineCliente")
    private String aggiuntaOrdineCliente(@RequestParam("rigaOrdine") Set<RigaOrdine> rigaOrdine,
                                    @RequestParam("quantita") Integer quantita,
                                    @RequestParam("cliente") Cliente cliente,
                                    @RequestParam("tipologiaRitiro") String tipologiaRitiro,
                                    @RequestParam("sede") Sede sede,
                                    @RequestParam("dataConsegnaDesiderata")LocalDate dataConsegnaDesiderata,
                                    @RequestParam("sedeDesiderata") Integer sedeDesiderata,
                                    @RequestParam("note") String note)
    {
        ordiniService.creazioneOrdine(rigaOrdine, quantita, cliente, tipologiaRitiro,
                sede, dataConsegnaDesiderata, sedeDesiderata, note);
        return "/Homepage";

    }

    @PostMapping("/aggiuntaOrdineOperatore")
    private String aggiuntaOrdineOperatore(@RequestParam("rigaOrdine") Set<RigaOrdine> rigaOrdine,
                                         @RequestParam("quantita") Integer quantita,
                                         @RequestParam("cliente") Cliente cliente,
                                         @RequestParam("tipologiaRitiro") String tipologiaRitiro,
                                         @RequestParam("sede") Sede sede,
                                         @RequestParam("dataConsegnaDesiderata")LocalDate dataConsegnaDesiderata,
                                         @RequestParam("sedeDesiderata") Integer sedeDesiderata,
                                         @RequestParam("note") String note)
    {
        ordiniService.creazioneOrdine(rigaOrdine, quantita, cliente, tipologiaRitiro,
                sede, dataConsegnaDesiderata, sedeDesiderata, note);
        return "LoggedHomepage";

    }

}
