package it.unisa.DryBlue.gestioneCliente.controller;


import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@SessionAttributes("utente")
@RequestMapping("/gestioneCliente")
public class GestioneClienteController {

    private final GestioneClienteService gestioneClienteService;


    @GetMapping("/clienti")
    public String trovaTuttiIClienti(final Model model) {
        model.addAttribute("clienti", gestioneClienteService.findTuttiIClienti());
        model.getAttribute("utente");
        return "/gestioneCliente/ListaClienti";
    }

    @GetMapping(value = "/clienti/dettagliCliente")
    public String dettagliCliente(final Model model) {
        model.getAttribute("utente");
        return "/gestioneCliente/DettagliCliente";

    }
    @PostMapping("/clienti/dettagliCliente")
    public String dettagli(@RequestParam("telefono") final String numTel, final Model model) {
        Cliente c = gestioneClienteService.findByTelefono(numTel);
        model.addAttribute("clientela", c);
        return "/gestioneCliente/DettagliCliente";
    }
}
