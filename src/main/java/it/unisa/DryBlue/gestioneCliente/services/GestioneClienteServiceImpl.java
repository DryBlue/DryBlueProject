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
    public List<Cliente> findTuttiIClienti() {
        return (List<Cliente>) clienteDAO.findAll();
    }

    @Override
    public Cliente findByTelefono(final String telefono) {
        return clienteDAO.findByNumeroTelefono(telefono);
    }



}
