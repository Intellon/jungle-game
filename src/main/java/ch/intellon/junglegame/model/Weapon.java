// Weapon.java
package ch.intellon.junglegame.model;

public class Weapon {
    private String name;
    private int damage;
    private Ammunition ammunition;

    public Weapon(String name, int damage, Ammunition ammunition) {
        this.name = name;
        this.damage = damage;
        this.ammunition = ammunition;
    }

    public int getDamage() {
        return damage + ammunition.getDamage();
    }

    public String getName() {
        return name;
    }

    public Ammunition getAmmunition() {
        return ammunition;
    }
}