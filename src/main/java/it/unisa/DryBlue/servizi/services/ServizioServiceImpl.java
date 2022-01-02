package it.unisa.DryBlue.servizi.services;

import it.unisa.DryBlue.ordini.domain.Sede;
import it.unisa.DryBlue.servizi.dao.MacchinarioDAO;
import it.unisa.DryBlue.servizi.dao.ServizioDAO;
import it.unisa.DryBlue.servizi.domain.Macchinario;
import it.unisa.DryBlue.servizi.domain.Servizio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServizioServiceImpl implements ServizioService{

    private final ServizioDAO servizioDAO;
    private final MacchinarioDAO macchinarioDAO;

    @Override
    public Macchinario aggiungiMacchinario(String matricola, String denominazione, String caratteristiche,
                                           String costruttore, String manutentore, String telefonoManutenzione,
                                           String stato, Sede sede) {
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
    public Boolean rimuoviMacchinario(String matricola) {
        Macchinario macchinario;
        macchinario = macchinarioDAO.findById(matricola).get();
        if(macchinario != null){
            macchinarioDAO.delete(macchinario);
            return true;
        }
        return false;
    }

    @Override
    public Boolean aggiornaStatoMacchinario(String matricola, String stato) {
        Macchinario macchinario;
        macchinario = macchinarioDAO.findById(matricola).get();
        if(macchinario != null){
            macchinario.setStato(stato);
            macchinarioDAO.save(macchinario);
            return true;
        }
        return false;
    }

    @Override
    public Servizio aggiungiServizio(String nome, String tipologia, String caratteristiche,
                                     double prezzo) {
        Servizio servizio = new Servizio();
        servizio.setNome(nome);
        servizio.setTipologia(tipologia);
        servizio.setCaratteristiche(caratteristiche);
        servizio.setPrezzo(prezzo);
        servizioDAO.save(servizio);
        return servizio;
    }

    @Override
    public Boolean rimuoviServizio(Integer idServizio) {
        Servizio servizio;
        servizio = servizioDAO.findById(idServizio).get();
        if(servizio != null){
            servizioDAO.delete(servizio);
            return true;
        }
        return false;
    }

    @Override
    public void modificaServizio(Integer idServizio, String nome, String tipologia, String caratteristiche, double prezzo) {
        Servizio servizio;
        servizio = servizioDAO.findById(idServizio).get();
        if(nome != null){
            servizio.setNome(nome);
        }
        if(tipologia != null){
            servizio.setTipologia(tipologia);
        }
        if(caratteristiche != null){
            servizio.setCaratteristiche(caratteristiche);
        }
        if(!Double.isNaN(prezzo)){
            servizio.setPrezzo(prezzo);
        }
        servizioDAO.save(servizio);
    }
}
