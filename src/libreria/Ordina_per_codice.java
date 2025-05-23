package libreria;

import java.util.ArrayList;
import java.util.Comparator;

public class Ordina_per_codice implements Ordinamento{

    @Override
    public void ordina(ArrayList<Libro> lista) {
        Comparator<Libro> compara_codici = new Comparator<Libro>() {
            public int compare(Libro l1, Libro l2) {
                return Integer.compare(l1.getCodice_ISBN(), l2.getCodice_ISBN());
            }
        };
        lista.sort(compara_codici);
    }
}
