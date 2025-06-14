import com.fasterxml.jackson.databind.ObjectMapper;
import libreria.risorse.Libro;
import libreria.risorse.Stato_della_lettura;
import libreria.risorse.Generi;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Memoria_secondaria_Test {

    @Test
    public void test_file() throws Exception {
        Libro libroOriginale = new Libro(
                "1984",
                "George Orwell",
                "9780451524935",
                Generi.FANTASY,
                5,
                Stato_della_lettura.LETTO
        );

        ObjectMapper mapper = new ObjectMapper();

        // Serializzazione
        String json = mapper.writeValueAsString(libroOriginale);

        // Deserializzazione
        Libro libroRicreato = mapper.readValue(json, Libro.class);

        // Verifiche
        assertEquals(libroOriginale.getTitolo(), libroRicreato.getTitolo());
        assertEquals(libroOriginale.getAutore(), libroRicreato.getAutore());
        assertEquals(libroOriginale.getCodice_ISBN(), libroRicreato.getCodice_ISBN());
        assertEquals(libroOriginale.getGenere(), libroRicreato.getGenere());
        assertEquals(libroOriginale.getValutazione(), libroRicreato.getValutazione());
        assertEquals(libroOriginale.getStatus(), libroRicreato.getStatus());
    }

}
