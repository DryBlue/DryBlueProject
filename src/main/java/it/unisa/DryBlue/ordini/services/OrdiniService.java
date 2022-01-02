package it.unisa.DryBlue.ordini.services;

import it.unisa.DryBlue.ordini.domain.Etichetta;
import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.servizi.domain.Servizio;

import java.time.LocalDate;
import java.util.List;

public interface OrdiniService {
    Ordine creazioneOrdine(Servizio servizio, Integer quantita, String tipologiaRitiro, LocalDate dataConsegnaDesiderata,
                           Integer sedeDesiderata, String note);

    void propostaModifica(LocalDate data, String indirizzoSede, Integer idOrdine);

    Boolean valutazionePropostaModifica(Integer idProposta);

    Boolean modificaOrdine(LocalDate data, String indirizzoSede, String stato, Integer idOrdine);

    List<Ordine> visualizzaOrdini(String filtro);

    Etichetta stampaEtichetta(Integer idOrdine);
}
