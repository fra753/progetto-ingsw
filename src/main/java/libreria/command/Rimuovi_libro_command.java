package libreria.command;

import libreria.iterazione.Iterator;
import libreria.risorse.Libreria;
import libreria.iterazione.Libreria_iterator;
import libreria.risorse.Libro;

public class Rimuovi_libro_command implements Command {
    private Libreria libreria;
    private Libro libro_rimosso;

    private String isbn;

    public Rimuovi_libro_command(Libreria libreria, String isbn) {
        this.libreria = libreria;
        this.isbn = isbn;
    }

    @Override
    public void execute() {
        Libro libro_rimosso = null;
        Iterator<Libro> it = new Libreria_iterator(libreria.getLibri());
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getCodice_ISBN().equals(isbn)) {
            libro_rimosso = l;
            break;}
        }
        if (libro_rimosso != null) {
            libreria.rimuovi_libro(isbn);
        }
    }

    @Override
    public void undo() {
        if (libro_rimosso != null) {
            libreria.aggiungiLibro(libro_rimosso);
        }
    }
}
