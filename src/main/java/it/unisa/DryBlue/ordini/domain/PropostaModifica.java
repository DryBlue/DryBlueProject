package it.unisa.DryBlue.ordini.domain;

import it.unisa.DryBlue.gestioneCliente.domain.Cliente;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class PropostaModifica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;


    private LocalDate dataProposta;

    @NonNull
    private String stato;


    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ordine ordine;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Sede sede;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cliente cliente;

}
