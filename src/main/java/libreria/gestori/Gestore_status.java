package libreria.gestori;

import libreria.iterazione.Iterator;
import libreria.Libro;
import libreria.Stato_della_lettura;

import java.util.List;

public abstract class Gestore_status {

    protected Gestore_status successivo;

    public void setSuccessivo(Gestore_status successivo) {
        this.successivo = successivo;
    }
    public void gestisci(Stato_della_lettura status, List<Libro> lista, Iterator<Libro> it) {
        if (successivo != null) {
            successivo.gestisci(status,lista,it);
        } else {
            System.out.println("Nessun gestore disponibile per: " + status);
        }
    }
}
