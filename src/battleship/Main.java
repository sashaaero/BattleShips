package battleship;

import javax.swing.*;

public class Main {
    public static final boolean DEV = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
