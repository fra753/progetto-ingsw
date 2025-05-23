package libreria;

import java.util.ArrayList;
import java.util.List;

public class Libreria {

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

}
