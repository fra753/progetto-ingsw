import libreria.Libro;
import libreria.Stato_della_lettura;
import libreria.Generi;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Libro_Test {

    @Test
    public void test_costruttore() {
        Libro libro = new Libro(
                "1984",
                "George Orwell",
                "1234567890",
                Generi.FANTASY,
                5,
                Stato_della_lettura.DA_LEGGERE
        );

        assertEquals("1984", libro.getTitolo());
        assertEquals("George Orwell", libro.getAutore());
        assertEquals("1234567890", libro.getCodice_ISBN());
        assertEquals(Generi.FANTASY, libro.getGenere());
        assertEquals(5, libro.getValutazione());
        assertEquals(Stato_della_lettura.DA_LEGGERE, libro.getStatus());
    }

    @Test
    public void test_modifica_valutazione() {
        Libro libro = new Libro();
        libro.modifica_valutazione(4);
        assertEquals(4, libro.getValutazione());
    }

    @Test
    public void test_modifica_status() {
        Libro libro = new Libro();
        libro.modifica_status(Stato_della_lettura.IN_LETTURA);
        assertEquals(Stato_della_lettura.IN_LETTURA, libro.getStatus());

        libro.modifica_status(Stato_della_lettura.LETTO);
        assertEquals(Stato_della_lettura.LETTO, libro.getStatus());
    }



}

