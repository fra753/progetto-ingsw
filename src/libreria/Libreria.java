package libreria;

import java.util.ArrayList;
import java.util.List;

public class Libreria {

    private static Libreria instance;
    private List<Libro> libri = new ArrayList<>();

    private Libreria() {}

    public static Libreria getInstance() {
        if (instance == null) {
            instance = new Libreria();
        }
        return instance;
    }
    public void aggiungiLibro(Libro libro) {
        libri.add(libro);
        System.out.println("Il libro "+ libro+  " è stato aggiunto");
    }
    public List<Libro> getLibri() {
        return libri;
    }

    public void rimuoviLibro(Libro libro) {
        libri.remove(libro);
        System.out.println("Il libro "+ libro+ " è stato rimosso");
    }

}
