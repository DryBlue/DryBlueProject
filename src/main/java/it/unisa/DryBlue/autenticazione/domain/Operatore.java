package it.unisa.DryBlue.autenticazione.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Random;

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

    public String generateString() {
        int leftLimit = 48; // numero '0'
        int rightLimit = 122; // lettera 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
