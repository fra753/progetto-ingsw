package libreria;

public class Gestore_in_lettura extends Gestore_status{

    public void gestisci (Stato_della_lettura status) {
        if (Stato_della_lettura.IN_LETTURA.equals(status)) {
            System.out.println("Gestore_letti gestisce");
        } else {
            super.gestisci(status);
        }
    }


}
