package it.unisa.DryBlue.servizi.controller;

import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.ordini.services.OrdiniService;
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
    private final OrdiniService ordineService;


    @GetMapping("/aggiuntaServizio")
    public String aggiunta(final Model model) {
        return "/servizi/aggiuntaServizio";
    }

    @PostMapping("/aggiuntaServizio")
    private String aggiuntaServizio(final Model model,
                                    final @RequestParam("name") String nome,
                                    final @RequestParam("tipologia") String tipologia,
                                    final @RequestParam("caratteristiche") String caratteristiche,
                                    final @RequestParam("prezzo") double prezzo) {
        servizioService.aggiungiServizio(nome, tipologia, caratteristiche, prezzo);
        model.getAttribute("utente");
        return "/servizi/ListaServizi";
    }

    @GetMapping("/aggiuntaMacchinario")
    public String aggiuntaMac(final Model model) {
        model.addAttribute("sedi", ordineService.visualizzaSedi());
        model.getAttribute("utente");
        return "/servizi/aggiuntaMacchinario";
    }

    @PostMapping("/aggiuntaMacchinario")
    private String aggiuntaMacchinario(final Model model,
                                       final @RequestParam("denomination") String denominazione,
                                       final @RequestParam("matricola") String matricola,
                                       final @RequestParam("costruttore") String costruttore,
                                       final @RequestParam("caratteristiche") String caratteristiche,
                                       final @RequestParam("manutentore") String manutentore,
                                       final @RequestParam("numeroMan") String telefonoManutenzione,
                                       final @RequestParam("indirizzoSede")String indirizzoSede) {
        servizioService.aggiungiMacchinario(denominazione, matricola, costruttore, caratteristiche,
                                            manutentore, telefonoManutenzione, "In funzione", ordineService.findByIndirizzo(indirizzoSede));
        model.getAttribute("utente");
        return trovaMacchinari(model);
    }

    @GetMapping("/ListaServizi")
    public String trovaServizi(final Model model) {
        model.addAttribute("servizi", servizioService.findServizi());
        model.getAttribute("utente");
        return "/servizi/ListaServizi";
    }

    @GetMapping("/ListaMacchinari")
    public String trovaMacchinari(final Model model) {
        model.addAttribute("macchinari", servizioService.findMacchinari());
        model.getAttribute("utente");
        return "/servizi/ListaMacchinari";
    }

    @PostMapping("/aggiornaStatoMacchinario")
    public String aggiornaStatoMacchinario(final Model model,
                                         final @RequestParam("matricola") String matricola,
                                         final @RequestParam("stato") String stato) {
        servizioService.aggiornaStatoMacchinario(matricola, stato);
        return trovaMacchinari(model);
    }


}
