// StartScreen.java
package ch.intellon.junglegame.view;

import ch.intellon.junglegame.model.Panther;
import ch.intellon.junglegame.model.Player;
import ch.intellon.junglegame.model.Predator;
import ch.intellon.junglegame.model.Tiger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartScreen extends JPanel {
    private MainWindow mainWindow;

    public StartScreen(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to Jungle Game!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));

        // Predator Selection
        JPanel predatorPanel = new JPanel();
        predatorPanel.setBorder(BorderFactory.createTitledBorder("Choose Your Predator"));
        JRadioButton tigerButton = new JRadioButton("Tiger");
        JRadioButton pantherButton = new JRadioButton("Panther");
        tigerButton.setSelected(true);
        ButtonGroup predatorGroup = new ButtonGroup();
        predatorGroup.add(tigerButton);
        predatorGroup.add(pantherButton);
        predatorPanel.add(tigerButton);
        predatorPanel.add(pantherButton);
        centerPanel.add(predatorPanel);

        // World Selection
        JPanel worldPanel = new JPanel();
        worldPanel.setBorder(BorderFactory.createTitledBorder("Choose the World"));
        JRadioButton jungleButton = new JRadioButton("Jungle");
        JRadioButton underwaterButton = new JRadioButton("Underwater World");
        jungleButton.setSelected(true);
        ButtonGroup worldGroup = new ButtonGroup();
        worldGroup.add(jungleButton);
        worldGroup.add(underwaterButton);
        worldPanel.add(jungleButton);
        worldPanel.add(underwaterButton);
        centerPanel.add(worldPanel);

        // Start Button
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener((ActionEvent e) -> {
            // Select Predator
            Predator predator;
            if (tigerButton.isSelected()) {
                predator = new Tiger();
            } else {
                predator = new Panther();
            }
            Player player = new Player(predator);
            mainWindow.setPlayer(player);

            // Select World
            String world;
            if (jungleButton.isSelected()) {
                world = "Jungle";
            } else {
                world = "Underwater World";
            }

            // Start Game
            mainWindow.startGame(world);
        });
        centerPanel.add(startButton);

        add(centerPanel, BorderLayout.CENTER);
    }
}