package libreria.risorse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import libreria.gestori.Gestore_da_leggere;
import libreria.gestori.Gestore_in_lettura;
import libreria.gestori.Gestore_letti;
import libreria.gestori.Gestore_status;
import libreria.iterazione.Aggregato;
import libreria.iterazione.Iterator;
import libreria.iterazione.Libreria_iterator;
import libreria.observer.Subject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Libreria extends Subject implements Aggregato {

    private static Libreria instanza;
    private List<Libro> libri ;
    private Libreria() {};

    public static Libreria getInstance() {
        if (instanza == null) {
            instanza = new Libreria();
        }
        return instanza;
    }

    public void aggiungiLibro(Libro libro) {
        libri.add(libro);
        System.out.println("Il libro "+ libro+  " è stato aggiunto");
        notificaObservers();
    }

    public List<Libro> getLibri() {
        return libri;
    }

    public void modifica_info(String isbn,int nuova_valutazione, Stato_della_lettura nuovo_status) {
        Iterator<Libro> it = crea_iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getCodice_ISBN().equals(isbn)) {
                l.modifica_status(nuovo_status);
                if (l.getStatus().equals(Stato_della_lettura.LETTO))
                    l.modifica_valutazione(nuova_valutazione);
            }
        }
        notificaObservers();
    }

    public void rimuovi_libro(String isbn) {
        Iterator<Libro> it = crea_iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getCodice_ISBN().equals(isbn)) {
                it.remove();
                notificaObservers();
                return;
            }
        }
    }

    public List<Libro> filtra_status(Stato_della_lettura status) {
        List<Libro> selezionati = new ArrayList<>();
        Iterator<Libro> it = crea_iterator();

        Gestore_status g1 = new Gestore_letti();
        Gestore_in_lettura g2 = new Gestore_in_lettura();
        Gestore_da_leggere g3 = new Gestore_da_leggere();
        g1.setSuccessivo(g2);
        g2.setSuccessivo(g3);
        g1.gestisci(status,selezionati,it);

        return selezionati;
    }

    public List<Libro> filtra_genere(Enum genere) {
        List<Libro> risultati = new ArrayList<>();
        Iterator<Libro> it = crea_iterator();
        while (it.hasNext()) {
            Libro libro = it.next();
            if (libro.getGenere().equals(genere)) {
                risultati.add(libro);
            }
        }
        return risultati;
    }


    private static final String FILE_PATH = "src/main/java/libreria/risorse/libreria.json";

    public void salvaSuFile() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(libri);
        Files.write(Paths.get(FILE_PATH), json.getBytes(StandardCharsets.UTF_8));
    }

    public void caricaDaFile()  {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                System.out.println("Il file non esiste, creando una nuova lista di libri.");
                libri = new ArrayList<>();
                return;
            }
            System.out.println("Sto cercando di caricare il file JSON: " + FILE_PATH);

            byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
            System.out.println("Contenuto del file JSON:");
            System.out.println(new String(jsonData, StandardCharsets.UTF_8));

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Libro.class);
            libri = mapper.readValue(jsonData, listType);
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file JSON: " + e.getMessage());
            libri = new ArrayList<>();
        }
    }


    @Override
    public Iterator<Libro> crea_iterator() {
        return new Libreria_iterator(libri);
    }
}
