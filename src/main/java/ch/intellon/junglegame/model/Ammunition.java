// Ammunition.java
package ch.intellon.junglegame.model;

public class Ammunition {
    private String type;
    private int damage;

    public Ammunition(String type, int damage) {
        this.type = type;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public String getType() {
        return type;
    }
}