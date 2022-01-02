package it.unisa.DryBlue.servizi.services;

import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;

public interface ServizioService {
    Macchinario aggiungiMacchinario(String matricola, String denominazione, String caratteristiche, String costruttore,
        String manutentore, String telefonoManutenzione, String stato, Sede sede);

    Boolean rimuoviMacchinario(String matricola);

    Boolean aggiornaStatoMacchinario(String matricola, String stato);

    Servizio aggiungiServizio(String nome, String tipologia, String caratteristiche, double prezzo);

    Boolean rimuoviServizio(Integer idServizio);

    void modificaServizio(Integer idServizio, String nome, String tipologia, String caratteristiche, double prezzo);
}
