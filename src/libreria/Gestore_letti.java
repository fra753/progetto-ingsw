package libreria;

import java.util.List;

public class Gestore_letti extends Gestore_status{

    public List<Libro> gestisci (Stato_della_lettura status, List<Libro> lista) {
        if (Stato_della_lettura.LETTO.equals(status)) {
            System.out.println("Gestore_letti gestisce");

        } else {
            return super.gestisci(status,lista);
        }
    }
}
