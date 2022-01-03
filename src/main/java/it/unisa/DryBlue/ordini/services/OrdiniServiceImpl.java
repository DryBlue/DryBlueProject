package it.unisa.DryBlue.ordini.services;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.dao.EtichettaDAO;
import it.unisa.DryBlue.ordini.dao.OrdineDAO;
import it.unisa.DryBlue.ordini.dao.PropostaModificaDAO;
import it.unisa.DryBlue.ordini.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrdiniServiceImpl implements OrdiniService{

    private final OrdineDAO ordineDAO;
    private final PropostaModificaDAO propostaModificaDAO;
    private final EtichettaDAO etichettaDAO;

    @Override
    public Ordine creazioneOrdine(Set<RigaOrdine> rigaOrdine, Integer quantita, Cliente cliente, String tipologiaRitiro,
                                  Sede sede, LocalDate dataConsegnaDesiderata, Integer sedeDesiderata, String note) {
        Ordine ordine = new Ordine();
        ordine.setDataConsegnaDesiderata(dataConsegnaDesiderata);
        ordine.setTipologiaRitiro(tipologiaRitiro);
        if(note != null){
            ordine.setNote(note);
        }
        ordine.setStato("Macchiato");
        ordine.setCliente(cliente);
        if(tipologiaRitiro.equals("In sede") && sede != null){
            ordine.setSede(sede);
        }
        ordineDAO.save(ordine);
        return ordine;
    }

    @Override
    public void propostaModifica(LocalDate data, Sede sede, Ordine ordine) {
        PropostaModifica propostaModifica = new PropostaModifica();
        propostaModifica.setOrdine(ordine);
        if(data != null){
            propostaModifica.setDataProposta(data);
        }else if(sede != null){
            propostaModifica.setSede(sede);
        }
        propostaModifica.setStato("In attesa");
        propostaModificaDAO.save(propostaModifica);

    }

    @Override
    public Boolean modificaOrdine(LocalDate data, Sede sede, String stato, Integer idOrdine) {
        Ordine ordine = ordineDAO.findById(idOrdine).get();
        if(data != null){
            ordine.setDataConsegnaDesiderata(data);
            ordineDAO.save(ordine);
            return true;
        }else if(sede != null){
            ordine.setSede(sede);
            ordineDAO.save(ordine);
            return true;
        }else if(stato != null){
            ordine.setStato(stato);
            ordineDAO.save(ordine);
            return true;
        }
        return false;
    }

    @Override
    public List<Ordine> visualizzaOrdini(Object obj, Cliente cliente) {
        String classe = String.valueOf(obj.getClass());
        List<Ordine> ordini, ordiniReturn = null;
        if(classe.equals("String")){
             ordini = ordineDAO.findAllByStato((String) obj);
             if(cliente != null){
                 for(Ordine o: ordini){
                     if(o.getCliente().equals(cliente)){
                         ordiniReturn.add(o);
                     }
                 }
                 return ordiniReturn;
             }
             return ordini;
        }
        else if(classe.equals("LocalDate")){
            ordini = ordineDAO.findAllByDataConsegnaDesiderata((LocalDate) obj);
            if(cliente != null){
                for(Ordine o: ordini){
                    if(o.getCliente().equals(cliente)){
                        ordiniReturn.add(o);
                    }
                }
                return ordiniReturn;
            }
            return ordini;
        }
        return null;
    }

    @Override
    public Etichetta stampaEtichetta(Ordine ordine) {
        Etichetta etichetta = new Etichetta();
        etichetta.setOrdine(ordine);
        etichetta.setSede(ordine.getSede());
        etichettaDAO.save(etichetta);
        return etichetta;
    }
}
