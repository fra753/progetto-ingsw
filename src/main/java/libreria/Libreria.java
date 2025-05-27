package libreria;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Libreria{

    private static Libreria instanza;
    private List<Libro> libri = new ArrayList<>();

    private Libreria() {}

    public static Libreria getInstance() {
        if (instanza == null) {
            instanza = new Libreria();
        }
        return instanza;
    }
    public void aggiungiLibro(Libro libro) {
        libri.add(libro);
        System.out.println("Il libro "+ libro+  " è stato aggiunto");
    }
    public List<Libro> getLibri() {
        return libri;
    }



    public void modifica_info(String isbn,int nuova_valutazione, Stato_della_lettura nuovo_status) {
        for (Libro l : libri) {
            if (l.getCodice_ISBN().equals(isbn)) {
                l.modifica_status(nuovo_status);
                if (l.getStatus().equals(Stato_della_lettura.LETTO))
                    l.modifica_valutazione(nuova_valutazione);
            }
        }
    }

    public void rimuovi_libro(String isbn) {
        Iterator<Libro> it = new Libreria_iterator(libri);
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getCodice_ISBN().equals(isbn)) {
                it.remove();
            }
        }
    }

    private Component radice;

    public Component getRadice() {
        return radice;
    }

    public Libreria (String nome) {
        this.radice = new Collezioni(nome);
    }

    public void add(Component c) {
        if (radice == null)
            radice = new Collezioni("Libreria");
        radice.add(c);
    }

    public void mostra_collezione(){
        radice.operation();
    }

    public Component get_collezione(String nome) {
        return cerca_collezione(radice,nome);
    }

    private Component cerca_collezione(Component componente, String nome) {
        if (componente instanceof Collezioni) {
            Collezioni collezione = (Collezioni) componente;
            if (collezione.getNome().equalsIgnoreCase(nome)) {
                return collezione;
            }
            for (int i = 0; i < collezione.get_size(); i++) {
                Component figlio = collezione.get_figlio(i);
                Component trovata = cerca_collezione(figlio, nome);
                if (trovata != null) {
                    return trovata;
                }
            }
        }
        return null;
    }

    private Map<String, List<Libro>> libriInAttesa = new HashMap<>();

    public void aggiungiLibroInAttesa(String nomeCollezione, Libro libro) {
        List<Libro> lista = libriInAttesa.get(nomeCollezione);
        if (lista == null) {
            lista = new ArrayList<>();
            libriInAttesa.put(nomeCollezione, lista);
        }
        lista.add(libro);

    }

    public List<Libro> getLibriInAttesa(String nomeCollezione) {
        return libriInAttesa.get(nomeCollezione);
    }

    public void rimuoviLibriInAttesa(String nomeCollezione) {
        libriInAttesa.remove(nomeCollezione);
    }

    public void mostra_libreria() {
        System.out.printf("%-30s %-20s %-15s %-15s\n",
            "Titolo", "Autore", "Genere", "Status");
        System.out.println();
        for (Libro libro : libri) {
            System.out.printf("%-30s %-20s %-15s %-15s\n",
            libro.getTitolo(),
            libro.getAutore(),
            libro.getGenere(),
            libro.getStatus());
        }
    }

    public List<Libro> filtra_genere(String genere) {
        List<Libro> selezionati = new ArrayList<>();
        Iterator<Libro> it = new Libreria_iterator(libri);

        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getGenere().equalsIgnoreCase(genere)) {
                selezionati.add(l);
            }
        }
        return selezionati;
    }

    public List<Libro> ricerca_per_autore(String autore) {
        List<Libro> selezionati = new ArrayList<>();
        Iterator<Libro> it = new Libreria_iterator(libri);

        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getAutore().equalsIgnoreCase(autore)) {
                selezionati.add(l);
            }
        }
        return selezionati;
    }

    public List<Libro> ricerca_per_titolo(String titolo) {
        List<Libro> selezionati = new ArrayList<>();
        Iterator<Libro> it = new Libreria_iterator(libri);
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getAutore().equalsIgnoreCase(titolo)) {
                selezionati.add(l);
            }
        }
        return selezionati;
    }

    public List<Libro> ricerca_per_status(Stato_della_lettura status) {
        List<Libro> selezionati = new ArrayList<>();
        Iterator<Libro> it = new Libreria_iterator(libri);

        Gestore_status g1 = new Gestore_letti();
        Gestore_in_lettura g2 = new Gestore_in_lettura();
        Gestore_da_leggere g3 = new Gestore_da_leggere();
        g1.setSuccessivo(g2);
        g2.setSuccessivo(g3);
        g1.gestisci(status,selezionati,it);
        return selezionati;
    }


    public void salva_sul_file(String percorso) throws IOException {
        ObjectMapper mappa = new ObjectMapper();
        Map<String, Object> dati = new HashMap<>();
        dati.put("radice", radice);
        dati.put("libriInAttesa", libriInAttesa);
        mappa.writeValue(new File(percorso), libri);
    }

    public void carica_dal_file(String percorso) throws IOException {
        ObjectMapper mappa = new ObjectMapper();
        //mappa.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); // serve per i tipi astratti
        Map<String, Object> dati = mappa.readValue(
                new File(percorso),
                new TypeReference<Map<String, Object>>() {}
        );
        radice = mappa.convertValue(dati.get("radice"), Collezioni.class);

        TypeReference<Map<String, List<Libro>>> tipoMappa = new TypeReference<>() {};
        libriInAttesa = mappa.convertValue(dati.get("libriInAttesa"), tipoMappa);
    }





}
