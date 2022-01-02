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

    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    public Integer id;

    @NonNull
    public String nome;

    @NonNull
    public String tipologia;

    @NonNull
    public String caratteristiche;

    @NonNull
    public double prezzo;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RigaOrdine rigaOrdine;
}
