package it.unisa.DryBlue.ordini.domain;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Ordine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull
    private LocalDate dataConsegnaDesiderata;

    @NonNull
    private String tipologiaRitiro;
    private String note;

    @NonNull
    private String stato;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Sede sede;


    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PropostaModifica propostaModifica;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Etichetta etichetta;

    @OneToMany(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<RigaOrdine> righeOrdine = new HashSet<>();

}
