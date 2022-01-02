package it.unisa.DryBlue.autenticazione.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Operatore implements Serializable {

    @Id
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String nome;

    @NonNull
    private String cognome;
}
