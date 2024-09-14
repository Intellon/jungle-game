// MainWindow.java
package ch.intellon.junglegame.view;

import ch.intellon.junglegame.model.Player;

import javax.swing.*;

public class MainWindow extends JFrame {
    private Player player;

    public MainWindow() {
        setTitle("Jungle Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showStartScreen();
        setVisible(true);
    }

    private void showStartScreen() {
        StartScreen startScreen = new StartScreen(this);
        setContentPane(startScreen);
        revalidate();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void startGame(String world) {
        if (world.equals("Jungle")) {
            JunglePanel junglePanel = new JunglePanel(this, player);
            setContentPane(junglePanel);
        } else if (world.equals("Underwater World")) {
            UnderwaterWorldPanel underwaterPanel = new UnderwaterWorldPanel(this, player);
            setContentPane(underwaterPanel);
        }
        revalidate();
    }
}