package libreria;

import java.util.ArrayList;
import java.util.Comparator;

public class Ordina_per_genere implements Ordinamento{
    @Override
    public void ordina(ArrayList<Libro> lista) {

        Comparator<Libro> compara_genere = new Comparator<Libro>() {
            public int compare(Libro l1, Libro l2) {
                return l1.getGenere().compareToIgnoreCase(l2.getGenere());
            }
        };
        lista.sort(compara_genere);
    }
}
