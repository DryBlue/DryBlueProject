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
public class Ruolo implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String OPERATORE_ROLE = "OPERATORE";
    public static final String CLIENTE_ROLE = "CLIENTE";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Exclude
    private Integer id;

    @NonNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruolo")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Utente> users = new HashSet<>();
}
