package libreria;

import java.util.ArrayList;
import java.util.Comparator;

public class Ordina_per_autore implements Ordinamento {
    @Override
    public void ordina(ArrayList<Libro> lista) {

        Comparator<Libro> compara_autori = new Comparator<Libro>() {
            public int compare(Libro l1, Libro l2) {
                return l1.getAutore().compareToIgnoreCase(l2.getAutore());
            }
        };
        lista.sort(compara_autori);
    }
}
