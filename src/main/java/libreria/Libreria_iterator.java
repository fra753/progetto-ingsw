package libreria;

import java.util.List;

public class Libreria_iterator implements Iterator<Libro> {

    private List<Libro> lista;
    private int corr = 0;
    private int rimosso = -1;

    public Libreria_iterator(List<Libro> lista) {
        this.lista = lista;
    }

    @Override
    public boolean hasNext() {
        return corr < lista.size();
    }

    @Override
    public Libro next() {
        if (!hasNext()) {
            throw new IllegalStateException("scansione lista conclusa");
        }
        rimosso = corr;
        return lista.get(corr++);
    }

    @Override
    public void remove() {
        if (rimosso == -1) {
            throw new IllegalStateException("next() non è stato ancora chiamato o remove() già usato");
        }
        lista.remove(rimosso);
        if ( rimosso < corr) {
            corr--;
        }
        rimosso = -1; // vieto il doppio remove
    }


}
