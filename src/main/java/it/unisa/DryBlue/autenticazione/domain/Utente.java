package it.unisa.DryBlue.autenticazione.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


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
}
