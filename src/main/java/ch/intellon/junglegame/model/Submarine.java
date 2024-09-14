// Submarine.java
package ch.intellon.junglegame.model;

public class Submarine extends Enemy {
    private Weapon weapon;
    private int coins;

    public Submarine() {
        super("Submarine", 70);
        this.weapon = new Weapon("Torpedo", 15, new Ammunition("Explosive", 10));
        this.coins = 15;
    }

    public Weapon dropWeapon() {
        return this.weapon;
    }

    public int getCoins() {
        return coins;
    }

    @Override
    public void die() {
        // Aktionen beim Tod des U-Boots
    }
}