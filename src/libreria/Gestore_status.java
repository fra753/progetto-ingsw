package libreria;

import java.util.ArrayList;
import java.util.List;

public abstract class Gestore_status {

    protected Gestore_status successivo;

    public void setSuccessivo(Gestore_status successivo) {
        this.successivo = successivo;
    }
    public List<Libro> gestisci(Stato_della_lettura status, List<Libro> lista) {
        if (successivo != null) {
            return successivo.gestisci(status,lista);
        } else {
            System.out.println("Nessun gestore disponibile per: " + status);
            return new ArrayList<>();
        }
    }
}
