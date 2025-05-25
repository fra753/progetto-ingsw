package libreria;

import java.util.ArrayList;
import java.util.List;

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



    public void modifica_info(int isbn,int nuova_valutazione, Stato_della_lettura nuovo_status) {
        for (Libro l : libri) {
            if (l.getCodice_ISBN() == isbn) {
                l.modifica_status(nuovo_status);
                if (l.getStatus().equals(Stato_della_lettura.LETTO))
                    l.modifica_valutazione(nuova_valutazione);
            }
        }
    }

    public void rimuovi_libro(int isbn) {
        Iterator<Libro> it = new Libreria_iterator(libri);
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getCodice_ISBN() == isbn) {
                it.remove();
            }
        }
    }

    private Component radice;

    public Libreria (String nome) {
        this.radice = new Collezioni(nome);
    }

    public void add(Component c) {
        radice.add(c);
    }

    public void mostra_collezione(){
        radice.operation();
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



}
