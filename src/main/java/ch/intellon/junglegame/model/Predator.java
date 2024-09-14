// Predator.java
package ch.intellon.junglegame.model;

public abstract class Predator {
    protected String name;
    protected int health;
    protected int damage;

    public Predator(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    // Einzigartige Fähigkeit des Raubtiers
    public abstract void specialAbility();

    // Getter und Setter
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }
}