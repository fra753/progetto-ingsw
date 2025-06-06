package libreria.gestori;

import libreria.iterazione.Iterator;
import libreria.Libro;
import libreria.Stato_della_lettura;

import java.util.List;

public class Gestore_letti extends Gestore_status {

    public void gestisci(Stato_della_lettura status, List<Libro> lista, Iterator<Libro> it) {
        if (status == Stato_della_lettura.LETTO) {
            while (it.hasNext()) {
                Libro l = it.next();
                if (l.getStatus() == Stato_della_lettura.LETTO) {
                    lista.add(l);
                }
            }
        } else if (successivo != null) {
            successivo.gestisci(status, lista, it);
        }
    }
}
