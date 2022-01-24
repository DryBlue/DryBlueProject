package it.unisa.DryBlue.gestioneCliente.domain;

import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.ordini.domain.PropostaModifica;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String numeroTelefono;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String indirizzo;

    @NonNull
    private String nome;

    @NonNull
    private String cognome;

    private String email;

    @OneToMany
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ordine> ordini = new HashSet<>();

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PropostaModifica propostaModifica;

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
