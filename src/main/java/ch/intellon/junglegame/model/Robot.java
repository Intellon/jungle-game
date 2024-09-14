// Robot.java
package ch.intellon.junglegame.model;

public class Robot extends Enemy {
    private Weapon weapon;
    private int coins;

    public Robot() {
        super("Robot", 50);
        this.weapon = new Weapon("Laser Cannon", 10, new Ammunition("Laser", 5));
        this.coins = 10;
    }

    public Weapon dropWeapon() {
        return this.weapon;
    }

    public int getCoins() {
        return coins;
    }

    @Override
    public void die() {
        // Aktionen beim Tod des Roboters
    }
}