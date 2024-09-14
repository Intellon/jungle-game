// Player.java
package ch.intellon.junglegame.model;

public class Player {
    private Predator predator;
    private Weapon weapon;
    private int coins;

    public Player(Predator predator) {
        this.predator = predator;
        this.coins = 0;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public int getCoins() {
        return coins;
    }

    public Predator getPredator() {
        return predator;
    }

    public void attack(Enemy enemy) {
        int totalDamage = predator.getDamage();
        if (weapon != null) {
            totalDamage += weapon.getDamage();
        }
        enemy.receiveDamage(totalDamage);
    }
}