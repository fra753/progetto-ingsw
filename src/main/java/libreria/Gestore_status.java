package libreria;

import java.util.ArrayList;
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
