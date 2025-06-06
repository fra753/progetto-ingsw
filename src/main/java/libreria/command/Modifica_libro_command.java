package libreria.command;

import libreria.*;
import libreria.iterazione.Iterator;
import libreria.iterazione.Libreria_iterator;

public class Modifica_libro_command implements Command {

    private Libreria libreria;
    private String isbn;
    private int nuovaValutazione;
    private Stato_della_lettura nuovoStatus;

    private int vecchiaValutazione;
    private Stato_della_lettura vecchioStatus;

    public Modifica_libro_command(Libreria libreria, String isbn, int nuovaValutazione, Stato_della_lettura nuovoStatus) {
        this.libreria = libreria;
        this.isbn = isbn;
        this.nuovaValutazione = nuovaValutazione;
        this.nuovoStatus = nuovoStatus;
    }

    @Override
    public void execute() {
        Iterator<Libro> it = new Libreria_iterator(libreria.getLibri());
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getCodice_ISBN().equals(isbn)) {
                vecchiaValutazione = l.getValutazione();
                vecchioStatus = l.getStatus();
                libreria.modifica_info(isbn, nuovaValutazione, nuovoStatus);
                break;
            }
        }
    }

    @Override
    public void undo() {
        libreria.modifica_info(isbn, vecchiaValutazione, vecchioStatus);
    }
}
