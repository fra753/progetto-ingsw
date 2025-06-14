package libreria.gui;

import javax.swing.*;

public class Client {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Applicazione app = new Applicazione();
            app.setVisible(true);
        });
    }
}
