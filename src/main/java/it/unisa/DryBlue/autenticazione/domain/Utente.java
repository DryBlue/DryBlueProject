package it.unisa.DryBlue.autenticazione.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Utente implements Serializable {

    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull
    private String username;
    @NonNull
    private String password;

    private String nome;
    private String cognome;
    private String indirizzo;
    private String cellulare;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ruolo> ruoli= new HashSet<>();

    public void addRole(final Ruolo r) {
        ruoli.add(r);
    }
}
