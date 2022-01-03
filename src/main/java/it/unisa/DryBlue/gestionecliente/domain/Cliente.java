package it.unisa.DryBlue.gestionecliente.domain;

import it.unisa.DryBlue.ordini.domain.Ordine;
import it.unisa.DryBlue.ordini.domain.PropostaModifica;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Cliente implements Serializable {

    private static final long serialVersionUID= 1L;

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
    private Set<Ordine> ordini=new HashSet<>();

    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PropostaModifica propostaModifica;

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
