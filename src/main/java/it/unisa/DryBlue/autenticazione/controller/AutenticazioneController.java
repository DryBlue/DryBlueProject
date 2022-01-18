package it.unisa.DryBlue.autenticazione.controller;

import it.unisa.DryBlue.autenticazione.dao.OperatoreDAO;
import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Operatore;
import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import it.unisa.DryBlue.autenticazione.services.AutenticazioneService;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;


@Controller
@RequiredArgsConstructor
@SessionAttributes("utente")
@RequestMapping("/autenticazione")
public class AutenticazioneController {

    @Autowired
    private UtenteDAO personaDAO;
    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private OperatoreDAO operatoreDAO;

    private Utente persona;

    @PostMapping("/HelloWorld")
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
     *
     * @param model il Model
     * @return la pagina dove è visualizzato
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String visualizzaLogin(final Model model) {
        model.addAttribute("utente", null);
        return "/autenticazione/Login";
    }

    /**
     * Implementa la funzionalità di login come utente.
     *
     * @param username dell'utente.
     * @param password password dell'utente.
     * @param model    la sessione in cui salvare l'utente.
     * @return rimanda alla pagina di home.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam final String username, @RequestParam final String password, final Model model) {
        Utente utente = autenticazioneService.login(username, password);
        if (utente.getRuolo().getName().equals("OPERATORE")) {
            model.addAttribute("utente", utente);
            return "LoggedHomepage";
        } else if (utente.getRuolo().getName().equals("CLIENTE")) {
            model.addAttribute("utente", utente);
            return "LoggedHomepage";
        }

        model.addAttribute("error", true);
        return "autenticazione/Login";

    }

    /**
     * Implementa la funzionalità che permette
     * di visualizzare la view della registrazione.
     *
     * @param model Model
     * @return la pagina registrazione.html
     * <p>
     * Autori: Miriam Ferrara e Sabrina Ceccarelli
     */
    @GetMapping("/registrazione")
    public String Registrazione(final Model model) {
        model.addAttribute("regUtente", new Utente());
        return "/autenticazione/registrazione";
    }


    /**
     * Implementa la funzionalità della registrazione del nuovo utente.
     *
     * @param m         la sessione in cui salvare l'utente
     * @param nome      del nuovo utente
     * @param cognome   del nuovo utente
     * @param indirizzo del nuovo utente
     * @param cellulare del nuovo utente
     * @return la pagina registrazioneSuccesso.html
     * <p>
     * Autori: Miriam Ferrara e Sabrina Ceccarelli
     */
    @PostMapping("/registrazione/registrazioneSuccesso")
    public String RegistrazioneProcesso(final Model m, final @RequestParam("nome") String nome,
                                        final @RequestParam("cognome") String cognome,
                                        final @RequestParam("indirizzo") String indirizzo,
                                        final @RequestParam("cellulare") String cellulare) {

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCognome(cognome);
        cliente.setIndirizzo(indirizzo);
        cliente.setNumeroTelefono(cellulare);
        cliente.setUsername(cliente.generateString());
        cliente.setPassword(cliente.generateString());

        clienteDAO.save(cliente);
        m.addAttribute("visualizza", cliente);

        System.out.println(clienteDAO.findByNome(cliente.getNome()));
        return "/autenticazione/registrazioneSuccesso";
    }

    @ModelAttribute("utente")
    public Utente utente() {
        return new Utente();
    }


    @Controller
    public class LogoutController {

        @RequestMapping(value = "/logout", method = RequestMethod.GET)
        public String logout(final HttpServletRequest request) {
            HttpSession httpSession = request.getSession();
            httpSession.invalidate();
            return "redirect:/";
        }
    }

    @PostMapping(value = "/newPassword")
    public String updatePassword(final Model model, final @RequestParam("newPassword") String newPassword) {
        Utente utente = (Utente) model.getAttribute("utente");
        if (utente.getRuolo().getName().equals("OPERATORE")) {
            Operatore operatore = operatoreDAO.findByUsername(utente.getUsername());
            operatore.setPassword(newPassword);
            operatoreDAO.save(operatore);
        } else if (utente.getRuolo().getName().equals("CLIENTE")) {
            Cliente cliente = clienteDAO.findByUsername(utente.getUsername());
            cliente.setPassword(newPassword);
            clienteDAO.save(cliente);
        }
        return "Homepage";
    }

    @GetMapping(value  = "/forgotPassword")
    public String dimenticatoPassword(final Model model) {
        model.getAttribute("utente");
        return "autenticazione/forgotPassword";
    }

    @PostMapping(value  = "/ReimpostaPassword")
    public String forgotPassword(final Model model,
                                 final @RequestParam("username") String username) {
        Utente utente = personaDAO.findByUsername(username);
         if (utente != null) {
            return "autenticazione/newPassword";
           } else {
            return "Homepage";
        }

    }

    @GetMapping(value = "/paginaReset")
    public String paginaReset(final Model model) {
        model.getAttribute("utente");
        return "autenticazione/newPassword";
    }
}
