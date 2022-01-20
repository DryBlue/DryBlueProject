package it.unisa.DryBlue.ordini.services;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import it.unisa.DryBlue.ordini.dao.*;
import it.unisa.DryBlue.ordini.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrdiniServiceImpl implements OrdiniService {

    private final OrdineDAO ordineDAO;
    private final PropostaModificaDAO propostaModificaDAO;
    private final EtichettaDAO etichettaDAO;
    private final SedeDAO sedeDAO;
    private final ClienteDAO clienteDAO;
    private final RigaOrdineDAO rigaOrdineDAO;

    @Override
    public Ordine creazioneOrdine(final Set<RigaOrdine> rigaOrdine,
                                  final String cliente,
                                  final String tipologiaRitiro,
                                  final String sede,
                                  final LocalDate dataConsegnaDesiderata,
                                  final String note) {
        Ordine ordine = new Ordine();
        ordine.setRigheOrdine(rigaOrdine);
        ordine.setDataConsegnaDesiderata(dataConsegnaDesiderata);
        ordine.setTipologiaRitiro(tipologiaRitiro);
        if (note != null) {
            ordine.setNote(note);
        }
        ordine.setStato("Macchiato");
        ordine.setCliente(clienteDAO.findByUsername(cliente));
        if (tipologiaRitiro.equals("In sede") && sede != null) {
            ordine.setSede(sedeDAO.findByIndirizzo(sede));
        }
        ordineDAO.save(ordine);
        return ordine;
    }

    @Override
    public void propostaModifica(final LocalDate data, final String sede, final Ordine ordine) {
        Cliente c = ordine.getCliente();
        PropostaModifica propostaModifica = new PropostaModifica();
        propostaModifica.setCliente(c);
        propostaModifica.setOrdine(ordine);
        if (data.compareTo(ordine.getDataConsegnaDesiderata()) == 0) {
            LocalDate d = ordine.getDataConsegnaDesiderata();
            Sede s = sedeDAO.findByIndirizzo(sede);
            propostaModifica.setSede(s);
            propostaModifica.setDataProposta(d);
        } else if (sede.equals(ordine.getSede().getIndirizzo())) {
            Sede s = ordine.getSede();
            propostaModifica.setDataProposta(data);
            propostaModifica.setSede(s);
        }

        propostaModifica.setStato("In attesa");
        propostaModificaDAO.save(propostaModifica);
        ordine.setPropostaModifica(propostaModifica);

    }

    @Override
    public Boolean modificaOrdine(final LocalDate data, final Sede sede,
                                  final String stato, final Integer idOrdine) {
        Ordine ordine = ordineDAO.findById(idOrdine).get();
        if (data != null) {
            ordine.setDataConsegnaDesiderata(data);
            ordineDAO.save(ordine);
            return true;
        } else if (sede != null) {
            ordine.setSede(sede);
            ordineDAO.save(ordine);
            return true;
        } else if (stato != null) {
            ordine.setStato(stato);
            ordineDAO.save(ordine);
            return true;
        }
        return false;
    }

    @Override
    public List<Ordine> visualizzaOrdiniFiltroOperatore(final String filtro) {
        if (filtro.equals("Consegnato")) {
            return ordineDAO.findAllByStato(filtro);
        } else {
            List<Ordine> ordini = ordineDAO.findAllByStato("Macchiato");
            ordini.addAll(ordineDAO.findAllByStato("Pronto"));
            ordini.addAll(ordineDAO.findAllByStato("Imbustato"));
            return ordini;
        }
    }

    @Override
    public List<Ordine> visualizzaOrdiniFiltroUtente(final String filtro, final String telefono) {
        List<Ordine> prova = new ArrayList<>();
        if (filtro.equals("Consegnato")) {
            List<Ordine> ordini = ordineDAO.findAllByStato(filtro);
            for (Ordine x : ordini) {
                if (x.getCliente().getNumeroTelefono().equals(telefono)) {
                    prova.add(x);
                }
            }
            return prova;
        } else {
            List<Ordine> ordini = ordineDAO.findAllByStato("Macchiato");
            ordini.addAll(ordineDAO.findAllByStato("Pronto"));
            ordini.addAll(ordineDAO.findAllByStato("Imbustato"));
            for (Ordine x : ordini) {
                if (x.getCliente().getNumeroTelefono().equals(telefono)) {
                    prova.add(x);
                }
            }
            return prova;
        }
    }

    @Override
    public List<Ordine> visualizzaOrdiniTotali() {
        return (List<Ordine>) ordineDAO.findAll();
    }

    @Override
    public Etichetta stampaEtichetta(final Ordine ordine) {
        Etichetta etichetta = new Etichetta();
        etichetta.setOrdine(ordine);
        etichetta.setSede(ordine.getSede());
        etichettaDAO.save(etichetta);
        return etichetta;
    }

    @Override
    public List<Sede> visualizzaSedi() {
        return (List<Sede>) sedeDAO.findAll();
    }

    @Override
    public Optional<Ordine> findById(final Integer idOrdine) {
        return ordineDAO.findById(idOrdine);
    }

    @Override
    public void creaRigaOrdine(final RigaOrdine riga) {
        rigaOrdineDAO.save(riga);
    }

    @Override
    public Sede findByIndirizzo(String indirizzo) {
        return sedeDAO.findByIndirizzo(indirizzo);
    }
}
