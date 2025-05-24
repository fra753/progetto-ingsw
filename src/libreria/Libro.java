package libreria;

public class Libro implements Component{

    private String titolo;
    private String autore;
    private int codice_ISBN;
    private String genere;
    private int valutazione;
    private Stato_della_lettura status;
    private boolean letto_almeno_una_volta;

    public Libro(String titolo, String autore, int codice_ISBN, String genere, int valutazione, Stato_della_lettura stato) {
        this.titolo = titolo;
        this.autore = autore;
        this.codice_ISBN = codice_ISBN;
        this.genere = genere;
        this.valutazione = valutazione;
        status = stato;
        letto_almeno_una_volta = false;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }
    public int getCodice_ISBN() {
        return codice_ISBN;
    }
    public String getGenere() {
        return genere;
    }
    public int getValutazione() {
        return valutazione;
    }
    public Stato_della_lettura getStatus() {
        return status;
    }
    public void modifica_status(Stato_della_lettura nuovo_status) {
        switch (nuovo_status) {
            case LETTO:
                status = Stato_della_lettura.LETTO;
                letto_almeno_una_volta = true;
                System.out.println(titolo + " è stato letto");
                break;
            case IN_LETTURA:
                status = Stato_della_lettura.IN_LETTURA;
                System.out.println(titolo + " è in lettura");
                break;

        }
    }
    public void modifica_valutazione(int nuova_valutazione) {
        if (! letto_almeno_una_volta) {
            throw new Libro_non_letto_eccezzione("non si può dare una valutazione");

        }
        valutazione = nuova_valutazione;
    }

    @Override
    public void operation() {
        System.out.println("libro " + titolo + " di " + autore);
    }

    @Override
    public void add(Component c) {
        throw new UnsupportedOperationException("Un libro non può contenere altri libri");
    }

    @Override
    public void remove(Component c) {
        throw new UnsupportedOperationException("Un libro non può contenere altri libri");
    }

    @Override
    public Component get_figlio(int i) {
        throw new UnsupportedOperationException("Un libro non ha figli");
    }
}
