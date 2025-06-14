import libreria.risorse.Generi;
import libreria.risorse.Stato_della_lettura;
import org.junit.jupiter.api.Test;
import java.util.List;
import libreria.risorse.Libro;
import libreria.risorse.Libreria;

import static org.junit.jupiter.api.Assertions.*;

public class Libreria_Test {

    private Libreria libreria;

    @Test
    void test_aggiunta() {
        Libro libro = new Libro("1984", "George Orwell", "7579347", Generi.AZIONE, 0, Stato_della_lettura.IN_LETTURA);

        libreria.aggiungiLibro(libro);

        assertTrue(libreria.getLibri().contains(libro));
    }

    @Test
    void test_rimozione() {

        Libro libro = new Libro("1984", "George Orwell","7579347", Generi.AZIONE, 0, Stato_della_lettura.IN_LETTURA);

        libreria.aggiungiLibro(libro);
        libreria.rimuovi_libro(libro.getCodice_ISBN());

        assertFalse(libreria.getLibri().contains(libro));
    }

    @Test
    void test_ricerca_titolo() {
        Libro libro = new Libro("Il nome della rosa", "Umberto Eco", "1234567890",
                Generi.GIALLO, 5, Stato_della_lettura.LETTO);
        libreria.aggiungiLibro(libro);

        List<Libro> trovati = libreria.rcercaPerTitolo("Il nome della rosa");

        assertEquals(2, trovati.size());
    }

    @Test
    void test_ricerca_autore() {
        Libro libro1 = new Libro("Libro 1", "Eco");
        Libro libro2 = new Libro("Libro 2", "Eco");
        Libro libro3 = new Libro("Libro 3", "Altri");

        libreria.aggiungiLibro(libro1);
        libreria.aggiungiLibro(libro2);
        libreria.aggiungiLibro(libro3);

        List<Libro> trovati = libreria.cercaPerAutore("Eco");

        assertEquals(2, trovati.size());
        assertTrue(trovati.contains(libro1));
        assertTrue(trovati.contains(libro2));
    }
}
