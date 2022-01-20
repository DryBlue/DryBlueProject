package it.unisa.DryBlue.autenticazione.controller;

import it.unisa.DryBlue.ordini.services.OrdiniService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequiredArgsConstructor
@SessionAttributes("utente")
public class PageController {

    private final OrdiniService ordiniService;

    @GetMapping("/")
    public String getHomepage() {
        return "Homepage";
    }

    @GetMapping("/LoggedHomepage")
    public String getLoggedHompage() {
        return "LoggedHomepage";
    }

    @GetMapping("/about")
    public String getSedi(final Model model) {
        model.addAttribute("sedi", ordiniService.visualizzaSedi());
        model.getAttribute("utente");
        return "About";
    }

    @GetMapping("/navbarCliente")
    public String goNavBar(final Model model) {
        model.getAttribute("utente");
        return "navbarCliente";
    }

    @GetMapping("/navbarOperatore")
    public String goNavBarOperatore(final Model model) {
        model.getAttribute("utente");
        return "navbarOperatore";
    }
}
