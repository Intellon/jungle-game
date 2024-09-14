// Enemy.java
package ch.intellon.junglegame.model;

public abstract class Enemy {
    protected String name;
    protected int health;

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public void receiveDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            die();
        }
    }

    public abstract void die();

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
}