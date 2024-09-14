// Game.java
package ch.intellon.junglegame;

import ch.intellon.junglegame.view.MainWindow;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}