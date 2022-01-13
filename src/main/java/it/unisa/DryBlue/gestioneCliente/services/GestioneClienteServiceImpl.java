package it.unisa.DryBlue.gestioneCliente.services;

import it.unisa.DryBlue.gestioneCliente.dao.ClienteDAO;
import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestioneClienteServiceImpl implements GestioneClienteService {

    private final ClienteDAO clienteDAO;

    @Override
    public Cliente aggiungiCliente(final String numTelefono, final String indirizzo,
                                   final String nome, final String cognome) {
        Cliente cliente = new Cliente();
        cliente.setNumeroTelefono(numTelefono);
        cliente.setIndirizzo(indirizzo);
        cliente.setNome(nome);
        cliente.setCognome(cognome);
        clienteDAO.save(cliente);
        return cliente;
    }

    @Override
    public Cliente reimpostaPassword(final String password, final Cliente cliente) {
        Cliente cliente1 = clienteDAO.findById(cliente.getNumeroTelefono()).get();
        cliente1.setPassword(password);
        clienteDAO.save(cliente1);
        return cliente1;
    }

    @Override
    public void rimuoviCliente(final Cliente cliente) {
        clienteDAO.delete(cliente);
    }

    @Override
    public List<Cliente> findTuttiIClienti() {
        return (List<Cliente>) clienteDAO.findAll();
    }

    @Override
    public Cliente findByTelefono(final String telefono) {
        return clienteDAO.findByNumeroTelefono(telefono);
    }

}
