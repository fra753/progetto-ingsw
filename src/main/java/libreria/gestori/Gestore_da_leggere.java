package libreria.gestori;

import libreria.iterazione.Iterator;
import libreria.risorse.Libro;
import libreria.risorse.Stato_della_lettura;

import java.util.List;

public class Gestore_da_leggere extends Gestore_status {

    public void gestisci(Stato_della_lettura status, List<Libro> lista, Iterator<Libro> it) {
        if (status == Stato_della_lettura.DA_LEGGERE) {
            while (it.hasNext()) {
                Libro l = it.next();
                if (l.getStatus() == Stato_della_lettura.DA_LEGGERE) {
                    lista.add(l);
                }
            }
        } else if (successivo != null) {
            successivo.gestisci(status, lista, it);
        }
    }

}
