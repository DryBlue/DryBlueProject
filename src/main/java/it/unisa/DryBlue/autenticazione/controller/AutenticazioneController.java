package it.unisa.DryBlue.autenticazione.controller;

import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.autenticazione.services.AutenticazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/autenticazione")
public class AutenticazioneController {

    @Autowired
    private UtenteDAO personaDAO;

    private Utente persona;

    @PostMapping ("/HelloWorld")
    @Transactional
    public String getDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        persona = personaDAO.findByUsername(auth.getName());
        return "HelloWorld";
    }

    /**
     * Il service per effettuare le operazioni di persistenza.
     */
    private final AutenticazioneService autenticazioneService;

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la view del login.
     * @param model il Model
     * @return la pagina dove è visualizzato
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String visualizzaLogin(final Model model) {
        model.addAttribute("loggedUser", null);
        return "/autenticazione/Login";
    }

    /**
     * Implementa la funzionalità di login come utente.
     * @param username dell'utente.
     * @param password password dell'utente.
     * @param model la sessione in cui salvare l'utente.
     * @return rimanda alla pagina di home.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam final String username,
                        @RequestParam final String password,
                        final Model model) {
        Utente utente = autenticazioneService.login(username, password);
        if (utente == null) {
            model.addAttribute("error", true);
            return "autenticazione/Login";
        } else {
            model.addAttribute("loggedUser", utente);
        }
        return "HelloWorld";

    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la view della registrazione.
     * @param model Model
     * @return la pagina registrazione.html
     *
     * Autori: Miriam Ferrara e Sabrina Ceccarelli
     */
    @GetMapping("/registrazione")
    public String Registrazione(Model model) {
        model.addAttribute("regUtente", new Utente());
        return "/autenticazione/registrazione";
    }


    /**
     * Implementa la funzionalità della registrazione del nuovo utente.
     * @param u utente
     * @param m la sessione in cui salvare l'utente
     * @param nome del nuovo utente
     * @param cognome del nuovo utente
     * @param indirizzo del nuovo utente
     * @param cellulare del nuovo utente
     * @return la pagina registrazioneSuccesso.html
     *
     * Autori: Miriam Ferrara e Sabrina Ceccarelli
     */
    @PostMapping("/registrazione/registrazioneSuccesso")
    public String RegistrazioneProcesso(Utente u, Model m,
                                        @RequestParam("nome") String nome,
                                        @RequestParam("cognome") String cognome,
                                        @RequestParam("indirizzo") String indirizzo,
                                        @RequestParam("cellulare") String cellulare) {

        Utente utent =new Utente();
        utent.setNome(nome);
        utent.setCognome(cognome);
        utent.setIndirizzo(indirizzo);
        utent.setCellulare(cellulare);

        Operatore op = new Operatore();
        String username= op.generateString();
        String password = op.generateString();

        u.setUsername(username);
        u.setPassword(password);

        utent.setUsername(username);
        utent.setPassword(password);

        personaDAO.save(u);
        m.addAttribute("visualizza",  utent);
        return "/autenticazione/registrazioneSuccesso";
    }

}
