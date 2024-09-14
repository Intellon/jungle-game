// JunglePanel.java
package ch.intellon.junglegame.view;

import ch.intellon.junglegame.model.Player;
import ch.intellon.junglegame.model.Robot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class JunglePanel extends JPanel {
    private MainWindow mainWindow;
    private Player player;
    private Robot currentEnemy;
    private BufferedImage backgroundImage;
    private JLabel playerLabel, enemyLabel;
    private JButton attackButton, abilityButton, fleeButton;
    private JLabel healthLabel, coinsLabel, enemyHealthLabel;

    public JunglePanel(MainWindow mainWindow, Player player) {
        this.mainWindow = mainWindow;
        this.player = player;
        initComponents();
        encounterEnemy();
    }

    private void initComponents() {
        setLayout(null); // Absolute positioning

        // Load background image
        backgroundImage = ImageLoader.loadImage("src/main/resources/images/jungle_background.jpg");

        // Load and set player image
        String predatorImagePath = "src/main/resources/images/" + player.getPredator().getName().toLowerCase() + ".png";
        ImageIcon predatorIcon = new ImageIcon(Objects.requireNonNull(ImageLoader.loadImage(predatorImagePath)));
        playerLabel = new JLabel(predatorIcon);
        playerLabel.setBounds(100, 300, 100, 100);
        add(playerLabel);

        // Enemy label
        enemyLabel = new JLabel();
        enemyLabel.setBounds(500, 300, 100, 100);
        add(enemyLabel);

        // Info labels
        healthLabel = new JLabel("Health: " + player.getPredator().getHealth());
        healthLabel.setBounds(10, 10, 150, 30);
        add(healthLabel);

        coinsLabel = new JLabel("Coins: " + player.getCoins());
        coinsLabel.setBounds(10, 40, 150, 30);
        add(coinsLabel);

        enemyHealthLabel = new JLabel("Enemy Health: ");
        enemyHealthLabel.setBounds(600, 10, 150, 30);
        add(enemyHealthLabel);

        // Action buttons
        attackButton = new JButton("Attack");
        attackButton.setBounds(250, 500, 100, 30);
        attackButton.addActionListener((ActionEvent e) -> attack());
        add(attackButton);

        abilityButton = new JButton("Use Ability");
        abilityButton.setBounds(360, 500, 120, 30);
        abilityButton.addActionListener((ActionEvent e) -> useAbility());
        add(abilityButton);

        fleeButton = new JButton("Flee");
        fleeButton.setBounds(490, 500, 80, 30);
        fleeButton.addActionListener((ActionEvent e) -> flee());
        add(fleeButton);
    }

    private void encounterEnemy() {
        currentEnemy = new Robot();
        enemyHealthLabel.setText("Enemy Health: " + currentEnemy.getHealth());

        // Load and set enemy image
        ImageIcon enemyIcon = new ImageIcon(Objects.requireNonNull(ImageLoader.loadImage("src/main/resources/images/robot.png")));
        enemyLabel.setIcon(enemyIcon);
    }

    private void attack() {
        player.attack(currentEnemy);
        enemyHealthLabel.setText("Enemy Health: " + currentEnemy.getHealth());

        if (currentEnemy.getHealth() > 0) {
            enemyAttack();
        } else {
            enemyDefeated();
        }
        updateLabels();
    }

    private void useAbility() {
        JOptionPane.showMessageDialog(this, "You used your special ability!");
        player.getPredator().specialAbility();
    }

    private void flee() {
        JOptionPane.showMessageDialog(this, "You fled from the battle!");
        encounterEnemy();
    }

    private void enemyAttack() {
        int damage = 10; // Beispielschaden
        player.getPredator().setHealth(player.getPredator().getHealth() - damage);
        JOptionPane.showMessageDialog(this, currentEnemy.getName() + " attacks you and deals " + damage + " damage.");
        if (player.getPredator().getHealth() <= 0) {
            JOptionPane.showMessageDialog(this, "You have been defeated!");
            endGame();
        }
    }

    private void enemyDefeated() {
        JOptionPane.showMessageDialog(this, "You defeated the enemy!");
        player.addCoins(currentEnemy.getCoins());
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to pick up the enemy's weapon?", "Pick Up Weapon", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            player.setWeapon(currentEnemy.dropWeapon());
            JOptionPane.showMessageDialog(this, "You picked up the " + player.getWeapon().getName() + ".");
        }
        updateLabels();
        encounterEnemy();
    }

    private void updateLabels() {
        healthLabel.setText("Health: " + player.getPredator().getHealth());
        coinsLabel.setText("Coins: " + player.getCoins());
    }

    private void endGame() {
        attackButton.setEnabled(false);
        abilityButton.setEnabled(false);
        fleeButton.setEnabled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}