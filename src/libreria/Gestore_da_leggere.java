package libreria;

public class Gestore_da_leggere extends Gestore_status{

    public void gestisci (Stato_della_lettura status) {
        if (Stato_della_lettura.DA_LEGGERE.equals(status)) {
            System.out.println("Gestore_da_leggere gestisce");
        } else {
            super.gestisci(status);
        }
    }
}
