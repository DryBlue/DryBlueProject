package it.unisa.DryBlue.ordini.domain;

import it.unisa.DryBlue.servizi.domain.Servizio;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class RigaOrdine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer quantita;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ordine ordine;

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Servizio servizio;
}
