package it.unisa.DryBlue.autenticazione.domain;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ruolo ruolo= new Ruolo();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente;

}
