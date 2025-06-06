package libreria.command;

import libreria.Libreria;
import libreria.Libro;

public class Aggiunta_libro_command  implements Command {

    private Libreria libreria;
    private Libro libro;

    public Aggiunta_libro_command(Libreria libreria, Libro libro) {
        this.libreria = libreria;
        this.libro = libro;
    }

    @Override
    public void execute() {
        libreria.aggiungiLibro(libro);
    }

    @Override
    public void undo() {
        libreria.rimuovi_libro(libro.getCodice_ISBN());
    }
}
