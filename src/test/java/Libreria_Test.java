
import libreria.risorse.Generi;
import libreria.risorse.Stato_della_lettura;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import libreria.risorse.Libro;
import libreria.risorse.Libreria;

import static org.junit.jupiter.api.Assertions.*;

public class Libreria_Test {

    private Libreria libreria2;
    private List<Libro> backupLibri;

    private static final String FILE_PATH = "src/test/java/libreria2.json";

    @BeforeEach
    void setup() {
        libreria2 = Libreria.getInstance();
        backupLibri = new ArrayList<>();
        List<Libro> libriCorrenti = libreria2.getLibri();
        if (libriCorrenti != null) {
            for (Libro l : libriCorrenti) {
                backupLibri.add(new Libro(l));
            }
        }
    }

    @AfterEach
    void restore() {
        List<Libro> libriCorrenti = libreria2.getLibri();
        if (libriCorrenti != null) {
            libriCorrenti.clear();
            libriCorrenti.addAll(backupLibri);
        }
    }

    @Test
    void test_aggiunta() {
        Libro libro = new Libro("1984", "George Orwell", "7579347", Generi.AZIONE, 0, Stato_della_lettura.IN_LETTURA);

        libreria2.aggiungiLibro(libro);

        assertTrue(libreria2.getLibri().contains(libro));
    }

    @Test
    void test_rimozione() {

        Libro libro = new Libro("1984", "George Orwell","7579347", Generi.AZIONE, 0, Stato_della_lettura.IN_LETTURA);

        libreria2.aggiungiLibro(libro);
        libreria2.rimuovi_libro(libro.getCodice_ISBN());

        assertFalse(libreria2.getLibri().contains(libro));
    }

    @Test
    void test_modifica_info() {
        Libro libro = new Libro("Harry Potter", "Rowling", "4827394", Generi.FANTASY, -1, Stato_della_lettura.DA_LEGGERE);
        libreria2.aggiungiLibro(libro);

        libreria2.modifica_info("4827394", 4, Stato_della_lettura.LETTO);

        assertEquals(Stato_della_lettura.LETTO, libro.getStatus());
        assertEquals(4, libro.getValutazione());
    }

    @Test
    void testFiltraStatus_LibriLetti() {
        Libro letto = new Libro("Letto", "Me", "L1", Generi.FANTASY, 5, Stato_della_lettura.LETTO);
        Libro inLettura = new Libro("In lettura", "Me", "L2", Generi.FANTASY, -1, Stato_della_lettura.IN_LETTURA);
        Libro daLeggere = new Libro("Da leggere", "Me", "L3", Generi.FANTASY, -1, Stato_della_lettura.DA_LEGGERE);

        libreria2.aggiungiLibro(letto);
        libreria2.aggiungiLibro(inLettura);
        libreria2.aggiungiLibro(daLeggere);

        List<Libro> risultati = libreria2.filtra_status(Stato_della_lettura.LETTO);
        assertEquals(1, risultati.size());
        assertEquals("Letto", risultati.get(0).getTitolo());
    }

    @Test
    void testFiltraGenere_Fantasy() {
        Libro fantasy = new Libro("Fantasy", "Me", "F1", Generi.FANTASY, -1, Stato_della_lettura.DA_LEGGERE);
        Libro azione = new Libro("Azione", "Me", "R1", Generi.AZIONE, -1, Stato_della_lettura.DA_LEGGERE);

        libreria2.aggiungiLibro(fantasy);
        libreria2.aggiungiLibro(azione);

        List<Libro> risultati = libreria2.filtra_genere(Generi.FANTASY);
        assertEquals(1, risultati.size());
        assertEquals("Fantasy", risultati.get(0).getTitolo());
    }

}
