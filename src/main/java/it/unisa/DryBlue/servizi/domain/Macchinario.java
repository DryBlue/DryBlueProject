package it.unisa.DryBlue.servizi.domain;

import it.unisa.DryBlue.ordini.domain.Sede;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Macchinario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String matricola;

    @NonNull
    private String denominazione;

    @NonNull
    private String caratteristiche;

    @NonNull
    private String costruttore;

    @NonNull
    private String manutentore;

    @NonNull
    private String telefonoManutenzione;

    @NonNull
    private String stato;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Sede sede;

}
