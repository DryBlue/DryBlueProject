package it.unisa.DryBlue.servizi.controller;

import it.unisa.DryBlue.servizi.services.ServizioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/servizio")
public class ServiziController {

    private final ServizioService servizioService;


    @GetMapping("/pagina")
    private String pagina(){
        return "/servizi/aggiuntaServizio";
    }

    @PostMapping("/aggiuntaServizio")
    private String aggiuntaServizio(@RequestParam("name") String nome,
                                    @RequestParam("tipologia") String tipologia,
                                    @RequestParam("caratteristiche") String caratteristiche,
                                    @RequestParam("prezzo") double prezzo){
        servizioService.aggiungiServizio(nome,tipologia,caratteristiche,prezzo);
        return "/servizi/ListaServizi";
    }

    @GetMapping("/servizi")
    public String trovaServizi(final Model model){
        model.addAttribute("servizi", servizioService.findServizi());
        return "/servizi/ListaServizi";
    }


}
