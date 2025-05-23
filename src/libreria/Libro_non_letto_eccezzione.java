package libreria;

public class Libro_non_letto_eccezzione extends RuntimeException{
    public Libro_non_letto_eccezzione(String msg) {
        super(msg);
    }
}
