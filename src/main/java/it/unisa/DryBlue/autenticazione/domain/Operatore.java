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
        final int leftLimit = 48; // numero '0'
        final int rightLimit = 122; // lettera 'z'
        final int targetStringLength = 10;
        final int filter1 = 57;
        final int filter2 = 65;
        final int filter3 = 90;
        final int filter4 = 97;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= filter1 || i >= filter2) && (i <= filter3 || i >= filter4))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


}
