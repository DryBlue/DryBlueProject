package it.unisa.DryBlue.ordini.controller;

import it.unisa.DryBlue.servizi.services.ServizioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("utente")
@RequestMapping("/ordini")
public class OrdiniController {


}
