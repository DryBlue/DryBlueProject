package it.unisa.DryBlue.servizi.services;

import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.servizi.dao.MacchinarioDAO;
import it.unisa.DryBlue.servizi.dao.ServizioDAO;
import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServizioServiceImpl implements ServizioService {

    private final ServizioDAO servizioDAO;
    private final MacchinarioDAO macchinarioDAO;

    @Override
    public Macchinario aggiungiMacchinario(final String matricola, final String denominazione,
                                           final String caratteristiche, final String costruttore,
                                           final String manutentore, final String telefonoManutenzione,
                                           final String stato, final Sede sede) {
        Macchinario macchinario = new Macchinario();
        macchinario.setMatricola(matricola);
        macchinario.setDenominazione(denominazione);
        macchinario.setCaratteristiche(caratteristiche);
        macchinario.setCostruttore(costruttore);
        macchinario.setManutentore(manutentore);
        macchinario.setTelefonoManutenzione(telefonoManutenzione);
        macchinario.setStato("In funzione");
        macchinario.setSede(sede);
        macchinarioDAO.save(macchinario);
        return macchinario;
    }


    @Override
    public Boolean aggiornaStatoMacchinario(final String matricola, final String stato) {
        Macchinario macchinario;
        macchinario = macchinarioDAO.findById(matricola).orElse(null);
        if (macchinario != null) {
            macchinario.setStato(stato);
            macchinarioDAO.save(macchinario);
            return true;
        }
        return false;
    }

    @Override
    public Servizio aggiungiServizio(final String nome, final String tipologia, final String caratteristiche,
                                     final double prezzo) {
        Servizio servizio = new Servizio();
        servizio.setNome(nome);
        servizio.setTipologia(tipologia);
        servizio.setCaratteristiche(caratteristiche);
        servizio.setPrezzo(prezzo);
        servizioDAO.save(servizio);
        return servizio;
    }

    @Override
    public List<Servizio> findServizi() {
        return (List<Servizio>) servizioDAO.findAll();
    }

    @Override
    public List<Macchinario> findMacchinari() {
        return (List<Macchinario>) macchinarioDAO.findAll();
    }

    @Override
    public Servizio findServizioById(final int id) {
        return servizioDAO.findById(id).get();
    }
}
