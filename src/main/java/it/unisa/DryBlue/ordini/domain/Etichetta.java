package it.unisa.DryBlue.ordini.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class Etichetta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Sede sede;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ordine ordine;
}
