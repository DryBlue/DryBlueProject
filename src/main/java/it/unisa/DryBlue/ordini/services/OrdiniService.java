package it.unisa.DryBlue.ordini.services;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.domain.*;
import it.unisa.DryBlue.servizi.domain.Servizio;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface OrdiniService {
    Ordine creazioneOrdine(Set<RigaOrdine> rigaOrdine, Integer quantita, Cliente cliente, String tipologiaRitiro,
                           Sede sede, LocalDate dataConsegnaDesiderata, Integer sedeDesiderata, String note);

    void propostaModifica(LocalDate data, Sede sede, Ordine ordine);

    Boolean modificaOrdine(LocalDate data, Sede sede, String stato, Integer idOrdine);

    List<Ordine> visualizzaOrdiniFiltroOperatore(String filtro);

    List<Ordine> visualizzaOrdiniFiltroUtente(String filtro, String telefono);

    List<Ordine> visualizzaOrdiniTotali();

    Etichetta stampaEtichetta(Ordine ordine);

    List<Sede> visualizzaSedi();
}
