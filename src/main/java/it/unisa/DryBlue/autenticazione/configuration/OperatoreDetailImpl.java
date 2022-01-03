package it.unisa.DryBlue.autenticazione.configuration;

import it.unisa.DryBlue.autenticazione.domain.Utente;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class OperatoreDetailImpl implements UserDetails {

    @NonNull
    private Utente user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();
        user.getRuoli().forEach(ruoli -> {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + ruoli.getName()));
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
