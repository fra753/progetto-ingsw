package libreria.ordinamenti;

import libreria.Libro;

import java.util.ArrayList;
import java.util.Comparator;

public class Ordina_per_codice implements Ordinamento {

    @Override
    public void ordina(ArrayList<Libro> lista) {
        Comparator<Libro> compara_codice = new Comparator<Libro>() {
            public int compare(Libro l1, Libro l2) {
                return l1.getCodice_ISBN().compareToIgnoreCase(l2.getCodice_ISBN());
            }
        };
        lista.sort(compara_codice);
    }
}
