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
    public Boolean rimuoviMacchinario(final String matricola) {
        Macchinario macchinario;
        macchinario = macchinarioDAO.findById(matricola).get();
        if (macchinario != null) {
            macchinarioDAO.delete(macchinario);
            return true;
        }
        return false;
    }

    @Override
    public Boolean aggiornaStatoMacchinario(final String matricola, final String stato) {
        Macchinario macchinario;
        macchinario = macchinarioDAO.findById(matricola).get();
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
    public Boolean rimuoviServizio(final Integer idServizio) {
        Servizio servizio;
        servizio = servizioDAO.findById(idServizio).get();
        if (servizio != null) {
            servizioDAO.delete(servizio);
            return true;
        }
        return false;
    }

    @Override
    public void modificaServizio(final Integer idServizio, final String nome, final String tipologia,
                                 final String caratteristiche, final double prezzo) {
        Servizio servizio;
        servizio = servizioDAO.findById(idServizio).get();
        if (nome != null) {
            servizio.setNome(nome);
        }
        if (tipologia != null) {
            servizio.setTipologia(tipologia);
        }
        if (caratteristiche != null) {
            servizio.setCaratteristiche(caratteristiche);
        }
        if (!Double.isNaN(prezzo)) {
            servizio.setPrezzo(prezzo);
        }
        servizioDAO.save(servizio);
    }

    @Override
    public List<Servizio> findServizi() {
        return (List<Servizio>) servizioDAO.findAll();
    }
}
