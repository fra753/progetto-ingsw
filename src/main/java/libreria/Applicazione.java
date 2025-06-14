package libreria;

import libreria.command.*;
import libreria.iterazione.Iterator;
import libreria.iterazione.Libreria_iterator;
import libreria.ordinamenti.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Applicazione extends JFrame implements Observer{

    private Libreria libreria = Libreria.getInstance();

    private List<Libro> libri_visualizzati = new ArrayList<>();

    private Invoker invoker = new Invoker();

    private JTextField campo_titolo;
    private JTextField campo_autore;
    private JTextField campo_isbn;
    private JComboBox<Generi> box_genere;
    private JComboBox<Integer> box_valutazione;
    private JComboBox<Stato_della_lettura> box_status;

    private DefaultListModel<String> modello_lista; // ciò che si mostra all'utente
    private JList<String> lista;

    public Applicazione() {
        setTitle("Gestione Libreria");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1500, 700);
        setLayout(new BorderLayout());
        JPanel pannelloInput = new JPanel(new GridLayout(8, 2));

        campo_titolo = new JTextField();
        campo_autore = new JTextField();
        campo_isbn = new JTextField();
        box_genere = new JComboBox<>(Generi.values());
        box_valutazione = new JComboBox<>(new Integer[]{-1, 1, 2, 3, 4, 5});
        box_status = new JComboBox<>(Stato_della_lettura.values());

        pannelloInput.add(new JLabel("Titolo:"));
        pannelloInput.add(campo_titolo);
        pannelloInput.add(new JLabel("Autore:"));
        pannelloInput.add(campo_autore);
        pannelloInput.add(new JLabel("ISBN:"));
        pannelloInput.add(campo_isbn);
        pannelloInput.add(new JLabel("Genere:"));
        pannelloInput.add(box_genere);
        pannelloInput.add(new JLabel("Valutazione:"));
        pannelloInput.add(box_valutazione);
        pannelloInput.add(new JLabel("Stato lettura:"));
        pannelloInput.add(box_status);
        add(pannelloInput, BorderLayout.NORTH);

        modello_lista = new DefaultListModel<>();
        lista = new JList<>(modello_lista);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        JPanel pannelloBottoni = new JPanel();
        JButton bottone_aggiungi = new JButton("Aggiungi libro");
        JButton bottone_salva = new JButton("Salva");
        JButton bottone_carica = new JButton("Carica");
        JButton bottone_rimuovi = new JButton("Rimuovi libro");
        JButton bottone_modifica = new JButton("Modifica libro");
        JButton bottone_cerca_titolo = new JButton("Cerca per titolo");
        JButton bottone_cerca_autore = new JButton("Cerca per autore");
        JButton bottone_filtra_status = new JButton("Filtra status");
        JButton bottone_filtra_genere = new JButton("Filtra genere");
        JButton bottone_ordina = new JButton("Ordina");
        JButton bottone_mostra_tutti = new JButton("Mostra tutti");
        JButton bottone_annulla_operazioni = new JButton("Annulla operazione");

        bottone_aggiungi.addActionListener(e -> aggiungiLibro());
        bottone_rimuovi.addActionListener(e -> rimuoviLibro());
        bottone_modifica.addActionListener(e -> modifica_libro());
        bottone_salva.addActionListener(e -> salva_libri());
        bottone_carica.addActionListener(e -> carica_libri());
        bottone_cerca_titolo.addActionListener(e -> cerca_per_titolo());
        bottone_cerca_autore.addActionListener(e -> cerca_per_autore());
        bottone_filtra_status.addActionListener(e -> filtra_per_status());
        bottone_filtra_genere.addActionListener(e -> filtra_per_genere());
        bottone_ordina.addActionListener(e -> ordina_lista());
        bottone_mostra_tutti.addActionListener(e -> mostra_tutti());
        bottone_annulla_operazioni.addActionListener(e -> invoker.annulla_ultimo());

        pannelloBottoni.add(bottone_aggiungi);
        pannelloBottoni.add(bottone_rimuovi);
        pannelloBottoni.add(bottone_modifica);
        pannelloBottoni.add(bottone_annulla_operazioni);
        pannelloBottoni.add(bottone_salva);
        pannelloBottoni.add(bottone_carica);
        pannelloBottoni.add(bottone_cerca_titolo);
        pannelloBottoni.add(bottone_cerca_autore);
        pannelloBottoni.add(bottone_filtra_status);
        pannelloBottoni.add(bottone_filtra_genere);
        pannelloBottoni.add(bottone_mostra_tutti);
        pannelloBottoni.add(bottone_ordina);
        add(pannelloBottoni, BorderLayout.SOUTH);

        libreria.aggiungiObserver(this);
    }

    private void aggiungiLibro() {
        try {
            String titolo = campo_titolo.getText().trim();
            String autore = campo_autore.getText().trim();
            String isbn = campo_isbn.getText().trim();
            if (titolo.isEmpty() || autore.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tutti i campi devono essere compilati");
                return;
            }
            Stato_della_lettura status_selezionato = (Stato_della_lettura) box_status.getSelectedItem();
            Integer valutazione_selezionata = (Integer) box_valutazione.getSelectedItem();
            if (status_selezionato == Stato_della_lettura.LETTO && valutazione_selezionata == -1) {
                JOptionPane.showMessageDialog(this, "Valutazione non valida: non puoi selezionare la valutazione di default per libri già letti");
                return;
            }
            Libro libro = new Libro(
                    campo_titolo.getText(),
                    campo_autore.getText(),
                    campo_isbn.getText(),
                    (Generi) box_genere.getSelectedItem(),
                    (Integer) box_valutazione.getSelectedItem(),
                    (Stato_della_lettura) box_status.getSelectedItem()
            );
            Command comando = new Aggiunta_libro_command(libreria, libro);
            invoker.esegui(comando);
            pulisciCampi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore nell'inserimento: " + ex.getMessage());
        }
    }

    private void rimuoviLibro() {
        JDialog finestra = new JDialog(this, "Seleziona libro da rimuovere", true);
        finestra.setSize(400, 300);
        finestra.setLayout(new BorderLayout());
        DefaultListModel<String> modelloSelezione = new DefaultListModel<>();
        JList<String> listaRimozione = new JList<>(modelloSelezione);
        JScrollPane scrollPane = new JScrollPane(listaRimozione);

        Iterator<Libro> it = new Libreria_iterator(libri_visualizzati);
        while (it.hasNext()) {
            Libro libro = it.next();
            modelloSelezione.addElement(libro.getTitolo() + " - " + libro.getAutore() + " (" + libro.getStatus() + ")");
        }
        JButton conferma = new JButton("Rimuovi selezionato");
        conferma.addActionListener(e -> {
            int indice = listaRimozione.getSelectedIndex();
            if (indice == -1) {
                JOptionPane.showMessageDialog(finestra, "Seleziona un libro da rimuovere");
                return;
            }
            Libro daRimuovere = libri_visualizzati.get(indice);
            int risposta = JOptionPane.showConfirmDialog(finestra,
                    "Sei sicuro di voler rimuovere:\n" +
                            daRimuovere.getTitolo() + " - " + daRimuovere.getAutore(),
                    "Conferma", JOptionPane.YES_NO_OPTION);
            if (risposta == JOptionPane.YES_OPTION) {
                Command comando = new Rimuovi_libro_command(libreria, daRimuovere.getCodice_ISBN());
                invoker.esegui(comando);
                finestra.dispose(); // chiudi la finestra
            }
        });
        finestra.add(scrollPane, BorderLayout.CENTER);
        finestra.add(conferma, BorderLayout.SOUTH);
        finestra.setLocationRelativeTo(this);
        finestra.setVisible(true);
    }

    private void modifica_libro() {
        int indiceSelezionato = lista.getSelectedIndex();
        if (indiceSelezionato == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona un libro dalla lista");
            return;
        }
        Libro libro = libri_visualizzati.get(indiceSelezionato);
        JPanel pannelloModifica = new JPanel(new GridLayout(2, 2));
        JComboBox<Stato_della_lettura> comboStatus = new JComboBox<>(Stato_della_lettura.values());
        comboStatus.setSelectedItem(libro.getStatus());
        JComboBox<Integer> comboValutazione = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        comboValutazione.setSelectedItem(libro.getValutazione());
        pannelloModifica.add(new JLabel("Stato lettura:"));
        pannelloModifica.add(comboStatus);
        pannelloModifica.add(new JLabel("Valutazione:"));
        pannelloModifica.add(comboValutazione);

        int result = JOptionPane.showConfirmDialog(this, pannelloModifica, "Modifica libro", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Stato_della_lettura nuovoStatus = (Stato_della_lettura) comboStatus.getSelectedItem();
            try {
                if ((libro.getStatus() == Stato_della_lettura.LETTO || libro.getStatus() == Stato_della_lettura.IN_LETTURA)
                        && nuovoStatus == Stato_della_lettura.DA_LEGGERE) {
                    throw new IllegalArgumentException("Non puoi tornare allo stato 'DA_LEGGERE' da 'LETTO' o 'IN_LETTURA'");
                }
                Command comando = new Modifica_libro_command(
                        libreria,
                        libro.getCodice_ISBN(),
                        (Integer) comboValutazione.getSelectedItem(),
                        (Stato_della_lettura) comboStatus.getSelectedItem()
                );
                invoker.esegui(comando);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Errore: " + e.getMessage());
            }
        }
    }

    private void cerca(String dato) {
        JDialog finestra = new JDialog(this, "Cerca per " + dato, true);
        finestra.setSize(400, 300);
        finestra.setLayout(new BorderLayout());
        JPanel pannelloSuperiore = new JPanel(new BorderLayout());
        JTextField campoRicerca = new JTextField();
        JButton bottoneCerca = new JButton("Cerca");
        pannelloSuperiore.add(new JLabel("Inserisci " + dato + " :"), BorderLayout.WEST);
        pannelloSuperiore.add(campoRicerca, BorderLayout.CENTER);
        pannelloSuperiore.add(bottoneCerca, BorderLayout.EAST);
        DefaultListModel<String> modelloRisultati = new DefaultListModel<>();
        JList<String> listaRisultati = new JList<>(modelloRisultati);
        JScrollPane scrollPane = new JScrollPane(listaRisultati);

        bottoneCerca.addActionListener(e -> {
            String testo = campoRicerca.getText().toLowerCase().trim();
            modelloRisultati.clear();
            Iterator<Libro> it = new Libreria_iterator(libri_visualizzati);
            while (it.hasNext()) {
                Libro libro = it.next();
                String valore = "";
                if (dato.equalsIgnoreCase("autore")) {
                    valore = libro.getAutore();
                } else if (dato.equalsIgnoreCase("titolo")) {
                    valore = libro.getTitolo();
                } else {
                    valore = "";
                }
                if (valore.toLowerCase().contains(testo)) {
                    modelloRisultati.addElement(libro.getTitolo() + " - " + libro.getAutore() + " - " +
                            libro.getGenere() + " (" + libro.getStatus() + ")");
                }
            }
            if (modelloRisultati.isEmpty()) {
                modelloRisultati.addElement("Nessun risultato trovato.");
            }
        });
        finestra.add(pannelloSuperiore, BorderLayout.NORTH);
        finestra.add(scrollPane, BorderLayout.CENTER);
        finestra.setLocationRelativeTo(this);
        finestra.setVisible(true);
    }

    private void cerca_per_autore() {
        cerca("autore");
    }

    private void cerca_per_titolo() {
        cerca("titolo");
    }

    private void filtra_per_status() {
        Stato_della_lettura statoSelezionato = (Stato_della_lettura) box_status.getSelectedItem();
        if (statoSelezionato == null) {
            JOptionPane.showMessageDialog(this, "Seleziona uno stato valido.");
            return;
        }
        List<Libro> risultati = libreria.filtra_status(statoSelezionato);
        modello_lista.clear();
        libri_visualizzati.clear();
        if (risultati.isEmpty()) {
            modello_lista.addElement("Nessun libro trovato con stato: " + statoSelezionato);
        } else {
            riempi(risultati);
        }
    }

    private void mostra_tutti(){
        modello_lista.clear();
        libri_visualizzati.clear();
        Iterator<Libro> it = new Libreria_iterator(libreria.getLibri());
        while (it.hasNext()) {
            Libro libro = it.next();
            libri_visualizzati.add(libro);
            modello_lista.addElement(libro.getTitolo() + " - " + libro.getAutore() + " - " +
                    libro.getGenere() + " (" + libro.getStatus() + ")");
        }
    }

    @Override
    public void aggiorna() {
        modello_lista.clear();
        Iterator<Libro> it = new Libreria_iterator(libreria.getLibri());
        while (it.hasNext()) {
            Libro libro = it.next();
            modello_lista.addElement(libro.getTitolo()+ " - " + libro.getAutore() + " - " + libro.getGenere() + " (" + libro.getStatus() + ")");
        }
    }

    private void filtra_per_genere() {
        Generi genere_selezionato = (Generi) box_genere.getSelectedItem();
        if (genere_selezionato == null) {
            JOptionPane.showMessageDialog(this, "Seleziona un genere valido");
            return;
        }
        List<Libro> risultati = libreria.filtra_genere(genere_selezionato);
        modello_lista.clear();
        libri_visualizzati.clear();
        if (risultati.isEmpty()) {
            modello_lista.addElement("Nessun libro trovato con genere: " + genere_selezionato);
        } else {
            riempi(risultati);
        }
    }

    private void riempi(List<Libro> risultati) {
        Iterator<Libro> it2 = new Libreria_iterator(risultati);
        while (it2.hasNext()) {
            Libro libro = it2.next();
            libri_visualizzati.add(libro);
            modello_lista.addElement(libro.getTitolo() + " - " + libro.getAutore() + " - " +
                    libro.getGenere() + " (" + libro.getStatus() + ")");
        }
    }


    private Ordinamento[] strategie_ordinamento = new Ordinamento[]{
            new Ordina_per_titolo(),
            new Ordina_per_autore(),
            new Ordina_per_codice(),
            new Ordina_per_genere(),
            new Ordina_per_valutazione(),
            new Ordina_per_status()
    };

    private String[] nomi_ordinamento = {
            "Titolo", "Autore", "Codice ISBN", "Genere", "Valutazione", "Stato"
    };


    private void ordina_lista() {
        JDialog finestra = new JDialog(this, "Seleziona criterio di ordinamento", true);
        finestra.setSize(300, 150);
        finestra.setLayout(new BorderLayout());
        JPanel pannelloCentro = new JPanel(new FlowLayout());


        JComboBox<String> comboOrdinamento = new JComboBox<>(nomi_ordinamento);
        pannelloCentro.add(new JLabel("Ordina per:"));
        pannelloCentro.add(comboOrdinamento);
        JButton bottoneConferma = new JButton("Applica");
        bottoneConferma.addActionListener(e -> {
            int indiceSelezionato = comboOrdinamento.getSelectedIndex();
            Ordinamento strategia = strategie_ordinamento[indiceSelezionato];
            strategia.ordina( (ArrayList<Libro>) libri_visualizzati);
            modello_lista.clear();
            Iterator<Libro> it = new Libreria_iterator(libri_visualizzati);
            while (it.hasNext()) {
                Libro libro = it.next();
                modello_lista.addElement(libro.getTitolo() + " - " + libro.getAutore() + " - " +
                        libro.getGenere() + " (" + libro.getStatus() + ")");
            }
            finestra.dispose();
        });
        finestra.add(pannelloCentro, BorderLayout.CENTER);
        finestra.add(bottoneConferma, BorderLayout.SOUTH);
        finestra.setLocationRelativeTo(this);
        finestra.setVisible(true);
    }

    private void carica_libri() {
        try {
            libreria.caricaDaFile();
            libri_visualizzati.clear();
            libri_visualizzati.addAll(libreria.getLibri());
            aggiorna_lista_della_GUI();
            JOptionPane.showMessageDialog(this, "Libreria caricata con successo.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore caricamento libreria: " + e.getMessage());
        }
    }

    private void salva_libri() {
        try {
            libreria.salvaSuFile();
            JOptionPane.showMessageDialog(this, "Libreria salvata con successo.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Errore salvataggio libreria: " + e.getMessage());
        }
    }

    private void aggiorna_lista_della_GUI() {
        modello_lista.clear();
        Iterator<Libro> it = new Libreria_iterator(libri_visualizzati);
        while (it.hasNext()) {
            Libro libro = it.next();
            String descrizione = libro.getTitolo() + " - " + libro.getAutore() + " - " +
                    libro.getGenere() + " (" + libro.getStatus() + ")";
            modello_lista.addElement(descrizione);
        }
    }

    private void pulisciCampi() {
        campo_titolo.setText("");
        campo_autore.setText("");
        campo_isbn.setText("");
        box_genere.setSelectedIndex(0);
        box_valutazione.setSelectedIndex(0);
        box_status.setSelectedIndex(0);
    }
}
