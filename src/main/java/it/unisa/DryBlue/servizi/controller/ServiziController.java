package it.unisa.DryBlue.servizi.controller;

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

    @PostMapping("/listaServizi")
    public String trovaServizi(final Model model){
        model.addAttribute("servizi", servizioService.findServizi());
        model.getAttribute("utente");
        return "/servizi/ListaServizi";
    }


}
