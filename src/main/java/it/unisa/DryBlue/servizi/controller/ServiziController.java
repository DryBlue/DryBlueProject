package it.unisa.DryBlue.servizi.controller;

import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.servizi.services.ServizioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@SessionAttributes("utente")
@RequestMapping("/servizio")
public class ServiziController {

    private final ServizioService servizioService;



    @GetMapping("/aggiuntaServizio")
    public String aggiunta(final Model model){
        return "/servizi/aggiuntaServizio";
    }

    @PostMapping("/aggiuntaServizio")
    private String aggiuntaServizio(final Model model,
                                    @RequestParam("name") String nome,
                                    @RequestParam("tipologia") String tipologia,
                                    @RequestParam("caratteristiche") String caratteristiche,
                                    @RequestParam("prezzo") double prezzo){
        servizioService.aggiungiServizio(nome,tipologia,caratteristiche,prezzo);
        model.getAttribute("utente");
        return "/servizi/ListaServizi";
    }

    @GetMapping("/aggiuntaMacchinario")
    public String aggiuntaMac(){
        return "/servizi/aggiuntaMacchinario";
    }

    @PostMapping("/aggiuntaMacchinario")
    private String aggiuntaMacchinario(final Model model,
                                       @RequestParam("denomination") String denominazione,
                                       @RequestParam("matricola") String matricola,
                                       @RequestParam("costruttore") String costruttore,
                                       @RequestParam("caratteristiche") String caratteristiche,
                                       @RequestParam("manutentore") String manutentore,
                                       @RequestParam("stato") String stato,
                                       @RequestParam("numeroMan") String telefonoManutenzione,
                                       @RequestParam("sede")Sede sede){
        servizioService.aggiungiMacchinario(denominazione, matricola, costruttore, caratteristiche,
                                            manutentore, stato, telefonoManutenzione, sede);
        model.getAttribute("utente");
        return "/servizi/listaMacchinari";
    }

    @GetMapping("/listaServizi")
    public String trovaServizi(final Model model){
        model.addAttribute("servizi", servizioService.findServizi());
        model.getAttribute("utente");
        return "/servizi/ListaServizi";
    }

}
