package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.domain.RigaOrdine;
import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.ordini.services.OrdiniService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ordini")
public class OrdiniController {
    private final OrdiniService ordiniService;


    @GetMapping("/pagina1")
    private String pagina1(){
        return "/ordini/aggiuntaOrdineCliente";
    }

    @GetMapping("/pagina2")
    private String pagina2(){
        return "/ordini/aggiuntaOrdineOperatore";
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
