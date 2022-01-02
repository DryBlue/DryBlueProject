package it.unisa.DryBlue.gestionecliente.domain;

import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.ordini.domain.PropostaModifica;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID= 1L;

    @Id
    private String numeroTelefono;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String indirizzo;

    @NonNull
    private String nome;

    @NonNull
    private String cognome;
    private String email;

    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ordine> ordini=new HashSet<>();

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PropostaModifica propostaModifica;


}
