package it.unisa.DryBlue.ordini.services;

import it.unisa.DryBlue.ordini.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrdiniService {
    Ordine creazioneOrdine(Set<RigaOrdine> rigaOrdine, String cliente, String tipologiaRitiro,
                           String sede, LocalDate dataConsegnaDesiderata, String note);

    void propostaModifica(LocalDate data, String sede, Ordine ordine);

    Boolean modificaOrdine(LocalDate data, Sede sede, String stato, Integer idOrdine);

    List<Ordine> visualizzaOrdiniFiltroOperatore(String filtro);

    List<Ordine> visualizzaOrdiniFiltroUtente(String filtro, String telefono);

    List<Ordine> visualizzaOrdiniTotali();

    Etichetta stampaEtichetta(Ordine ordine);

    List<Sede> visualizzaSedi();

    Ordine findById(Integer idOrdine);

    void creaRigaOrdine(RigaOrdine riga);

    Sede findByIndirizzo(String indirizzo);
}
