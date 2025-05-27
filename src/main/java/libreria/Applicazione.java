package libreria;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Applicazione extends JFrame {

    private Libreria libreria = Libreria.getInstance();
    // per il singleton

    private JTextField campo_titolo;
    private JTextField campo_autore;
    private JTextField campo_isbn;
    private JTextField campo_genere;
    private JComboBox<Integer> box_valutazione;
    private JComboBox<Stato_della_lettura> box_status;
    private JCheckBox box_letto_almeno_una_volta;
    private JTextField collezione;
    private DefaultListModel<String> modello_lista;
    private JList<String> lista;

    public Applicazione() {
        setTitle("Gestione Libreria");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Pannello input
        JPanel pannelloInput = new JPanel(new GridLayout(8, 2));

        campo_titolo = new JTextField();
        campo_autore = new JTextField();
        campo_isbn = new JTextField();
        campo_genere = new JTextField();
        box_valutazione = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        box_status = new JComboBox<>(Stato_della_lettura.values());
        box_letto_almeno_una_volta = new JCheckBox();
        collezione = new JTextField();

        pannelloInput.add(new JLabel("Titolo:")); pannelloInput.add(campo_titolo);
        pannelloInput.add(new JLabel("Autore:")); pannelloInput.add(campo_autore);
        pannelloInput.add(new JLabel("ISBN:")); pannelloInput.add(campo_isbn);
        pannelloInput.add(new JLabel("Genere:")); pannelloInput.add(campo_genere);
        pannelloInput.add(new JLabel("Valutazione:")); pannelloInput.add(box_valutazione);
        pannelloInput.add(new JLabel("Stato lettura:")); pannelloInput.add(box_status);
        pannelloInput.add(new JLabel("Letto_almeno_una_volta:"));pannelloInput.add(box_letto_almeno_una_volta);
        pannelloInput.add(new JLabel("Collezione:"));pannelloInput.add(collezione);

        add(pannelloInput, BorderLayout.NORTH);


        modello_lista = new DefaultListModel<>();
        lista = new JList<>(modello_lista);
        add(new JScrollPane(lista), BorderLayout.CENTER);


        JPanel pannelloBottoni = new JPanel();

        JButton bottoneAggiungi = new JButton("Aggiungi libro");
        JButton bottoneSalva = new JButton("Salva");
        JButton bottoneCarica = new JButton("Carica");
        JButton bottone_crea_collezione = new JButton("Crea collezione");

        bottoneAggiungi.addActionListener(e -> aggiungiLibro());
        bottoneSalva.addActionListener(e -> salvaLibri());
        bottoneCarica.addActionListener(e -> caricaLibri());
        bottone_crea_collezione.addActionListener(e -> aggiungiCollezione());

        pannelloBottoni.add(bottoneAggiungi);
        pannelloBottoni.add(bottoneSalva);
        pannelloBottoni.add(bottoneCarica);
        pannelloBottoni.add(bottone_crea_collezione);

        add(pannelloBottoni, BorderLayout.SOUTH);
    }

    private void aggiungiLibro() {
        try {

            Libro libro = new Libro(
                    campo_titolo.getText(),
                    campo_autore.getText(),
                    campo_isbn.getText(),
                    campo_genere.getText(),
                    (Integer) box_valutazione.getSelectedItem(),
                    (Stato_della_lettura) box_status.getSelectedItem(),
                    box_letto_almeno_una_volta.isSelected()
            );
            String nome_collezione = collezione.getText().trim();
            boolean aggiunto = false;

            if (!nome_collezione.isEmpty()) {
                Component c = libreria.get_collezione(nome_collezione);
                if (c != null && c instanceof Collezioni) {
                    c.add(libro);
                    aggiunto = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Collezione non trovata. Il libro sarà aggiunto alla libreria principale");
                    libreria.aggiungiLibroInAttesa(nome_collezione, libro);
                    aggiunto = true;
                }
            }
            if (! aggiunto) {
            libreria.aggiungiLibro(libro);
        }
            modello_lista.addElement(libro.getTitolo() + " - " + libro.getAutore() + " - "+ libro.getGenere()+" (" + libro.getStatus() + ")");
            pulisciCampi();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore nell'inserimento: " + ex.getMessage());
        }
    }

    private void aggiungiCollezione() {
        String nomeCollezione = collezione.getText().trim();

        if (!nomeCollezione.isEmpty()) {
            Collezioni nuova = new Collezioni(nomeCollezione);
            libreria.add(nuova);
            modello_lista.addElement("Collezione: " + nomeCollezione);
            collezione.setText("");

            // Aggiunge eventuali libri in attesa a questa collezione
            List<Libro> inAttesa = libreria.getLibriInAttesa(nomeCollezione);
            if (inAttesa != null) {
                for (Libro libro : inAttesa) {
                    nuova.add(libro);
                    modello_lista.addElement(libro.getTitolo() + " - " + libro.getAutore() + " (aggiunto a " + nomeCollezione + ")");
                }
                libreria.rimuoviLibriInAttesa(nomeCollezione);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Inserisci un nome per la collezione.");
        }
    }




    private void salvaLibri() {
        try {
            libreria.salva_sul_file("libri.json");
            JOptionPane.showMessageDialog(this, "Libri salvati correttamente!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Errore nel salvataggio.");
        }
    }

    private void caricaLibri() {
        try {
            libreria.carica_dal_file("libri.json");
            modello_lista.clear();
            aggiorna_lista(libreria.getRadice());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento.");
        }
    }

    private void aggiorna_lista(Component componente) {
        if (componente instanceof Libro libro) {
            modello_lista.addElement(libro.getTitolo() + " - " + libro.getAutore() + " - " + libro.getGenere());
        } else if (componente instanceof Collezioni collezione) {
            modello_lista.addElement("Collezione: " + collezione.getNome());
            for (Component figlio : collezione.prendi_componenti()) {
                aggiorna_lista(figlio);
            }
        }
    }

    private void pulisciCampi() {
        // così non devo liberare i campi manulmente
        campo_titolo.setText("");
        campo_autore.setText("");
        campo_isbn.setText("");
        campo_genere.setText("");
        collezione.setText("");

        box_valutazione.setSelectedIndex(0);
        box_status.setSelectedIndex(0);
        box_letto_almeno_una_volta.setSelected(false);
    }


    public static void main(String[] args) {
        System.out.println("======== AVVIO APPLICAZIONE ========");

        Libreria libreria = Libreria.getInstance();
        if (libreria.getRadice() == null) {
            libreria = new Libreria("Libreria Principale");
            System.out.println("Libreria radice creata con successo.");
        }
        Collezioni harryPotter = new Collezioni("Harry Potter");
        Collezioni signoreAnelli = new Collezioni("Il Signore degli Anelli");
        Collezioni cronacheGhiaccioFuoco = new Collezioni("Le Cronache del Ghiaccio e del Fuoco");

        // Aggiunta delle saghe alla libreria principale
        libreria.add(harryPotter);
        libreria.add(signoreAnelli);
        libreria.add(cronacheGhiaccioFuoco);

        // Aggiunta libri a "Harry Potter"
        harryPotter.add(new Libro("Harry Potter 1", "Rowling", "1111111111", "Fantasy", 5, Stato_della_lettura.LETTO, true));
        harryPotter.add(new Libro("Harry Potter 2", "Rowling", "1111111112", "Fantasy", 4, Stato_della_lettura.LETTO, true));

        // Aggiunta libri a "Il Signore degli Anelli"
        signoreAnelli.add(new Libro("La Compagnia dell’Anello", "J.R.R. Tolkien", "2222222221", "Fantasy", 5, Stato_della_lettura.LETTO, true));
        signoreAnelli.add(new Libro("Le Due Torri", "J.R.R. Tolkien", "2222222222", "Fantasy", 5, Stato_della_lettura.LETTO, true));
        signoreAnelli.add(new Libro("Il Ritorno del Re", "J.R.R. Tolkien", "2222222223", "Fantasy", 5, Stato_della_lettura.IN_LETTURA, true));

        // Aggiunta libri a "Le Cronache del Ghiaccio e del Fuoco"
        cronacheGhiaccioFuoco.add(new Libro("Il Trono di Spade", "George R. R. Martin", "3333333331", "Fantasy", 4, Stato_della_lettura.LETTO, true));
        cronacheGhiaccioFuoco.add(new Libro("Il Grande Inverno", "George R. R. Martin", "3333333332", "Fantasy", 5, Stato_della_lettura.IN_LETTURA, true));

        // Mostra l’intera struttura gerarchica
        libreria.mostra_collezione();

        // Avvia interfaccia grafica
        SwingUtilities.invokeLater(() -> {
            Applicazione app = new Applicazione();
            app.setVisible(true);
            System.out.println("Interfaccia avviata.");
        });

        System.out.println("=============================================");
    }




}
