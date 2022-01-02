package it.unisa.DryBlue.ordini.domain;

import it.unisa.DryBlue.servizi.domain.Macchinario;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Sede implements Serializable {

    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Integer id;

    @NonNull
    public String indirizzo;

    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ordine> ordini=new HashSet<>();

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PropostaModifica propostaModifica;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Etichetta etichetta;

    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Macchinario> macchinari= new HashSet<>();

}
