package it.unisa.DryBlue.gestioneCliente.controller;


import it.unisa.DryBlue.gestioneCliente.services.GestioneClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GestioneClienteController {

    private final GestioneClienteService gestioneClienteService;

    @GetMapping("/pagine")
    public String getPagina(final Model model){
        return "ListaClienti";
    }


    @GetMapping("/clienti")
    public String trovaTuttiIClienti(final Model model){
        model.addAttribute("clienti", gestioneClienteService.findTuttiIClienti());
        return "ListaClienti";
    }

    @GetMapping("/clienti/modifica")
    public String Modifica() {
        return "HelloWorld";
    }

    @GetMapping("/clienti/rimuovi")
    public String Rimuovi() {
        return "HelloWorld";
    }

    @PostMapping("/clienti/dettagliCliente")
    public String dettagli(@RequestParam ("telefono") String numTel, final Model model) {
        System.out.println(numTel);
        model.addAttribute("telefono", numTel);
        model.addAttribute("clientela", gestioneClienteService.findByTelefono(numTel));
        return "DettagliCliente";
    }

}
