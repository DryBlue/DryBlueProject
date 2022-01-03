package it.unisa.DryBlue.autenticazione.configuration;

import it.unisa.DryBlue.autenticazione.dao.UtenteDAO;
import it.unisa.DryBlue.autenticazione.domain.Utente;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userDetailServices")
public class OperatoreDetailServiceImpl implements UserDetailsService {

    @NonNull
    private UtenteDAO userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente selectedUser = userDao.findByUsername(username);
        return new OperatoreDetailImpl(selectedUser);
    }
}
