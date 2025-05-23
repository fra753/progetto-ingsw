package libreria;

import java.util.ArrayList;
import java.util.Comparator;

public class Ordina_per_titolo implements Ordinamento {
    @Override
    public void ordina(ArrayList<Libro> lista) {

        Comparator<Libro> compara_titoli = new Comparator<Libro>() {
            public int compare(Libro l1, Libro l2) {
                return l1.getTitolo().compareToIgnoreCase(l2.getTitolo());
            }
        };
        lista.sort(compara_titoli);
    }
}
