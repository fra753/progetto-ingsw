package libreria;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Libro  {
    private String titolo;
    private String autore;
    private String codice_ISBN;
    private Generi genere;
    private int valutazione;
    private Stato_della_lettura status;

    public Libro(@JsonProperty("titolo") String titolo,
                 @JsonProperty("autore") String autore,
                 @JsonProperty("codice_ISBN") String codiceISBN,
                 @JsonProperty("genere") Generi genere,
                 @JsonProperty("valutazione") int valutazione,
                 @JsonProperty("status") Stato_della_lettura status) {
        this.titolo = titolo;
        this.autore = autore;
        this.codice_ISBN = codiceISBN;
        this.genere = genere;
        this.valutazione = valutazione;
        this.status = status;
    }
    // per caricare libri da file json
    public Libro(){}

    public String getTitolo() {
        return titolo;
    }
    public String getAutore() {
        return autore;
    }
    public String getCodice_ISBN() {
        return codice_ISBN;
    }
    public Generi getGenere() {
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
                System.out.println(titolo + " è stato letto");
                break;
            case IN_LETTURA:
                status = Stato_della_lettura.IN_LETTURA;
                System.out.println(titolo + " è in lettura");
                break;
        }
    }

    public void modifica_valutazione(int nuova_valutazione) {
        valutazione = nuova_valutazione;
    }


}
