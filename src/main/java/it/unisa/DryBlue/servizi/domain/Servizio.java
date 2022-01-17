package it.unisa.DryBlue.servizi.domain;

import it.unisa.DryBlue.ordini.domain.RigaOrdine;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Servizio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NonNull
    private String nome;

    @NonNull
    private String tipologia;

    @NonNull
    private String caratteristiche;

    @NonNull
    private double prezzo;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RigaOrdine rigaOrdine;
}
