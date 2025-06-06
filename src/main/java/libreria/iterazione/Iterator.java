package libreria.iterazione;

public interface Iterator<Libro> {

    boolean hasNext();
    Libro next();
    void remove();
}
