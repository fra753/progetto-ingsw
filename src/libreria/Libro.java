package libreria;

public class Libro {

    private String titolo;
    private String autore;
    private int codice_ISBN;
    private String genere;
    private int valutazione;
    private Stato_della_lettura stato;

    public Libro(String titolo, String autore, int codice_ISBN, String genere, int valutazione, Stato_della_lettura stato) {
        this.titolo = titolo;
        this.autore = autore;
        this.codice_ISBN = codice_ISBN;
        this.genere = genere;
        this.valutazione = valutazione;
        this.stato = stato;
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {}
}
