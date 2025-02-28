package elements.Animals;

import elements.Entity;
import elements.GameMap;
import java.util.List;
import util.MoveRequest;
import util.PathFinder;

public abstract class Creature extends Entity {
    private int health;
    private int hunger;
    private final int maxHp;
    private final int maxHunger;

    public Creature(String display, int maxHp, int maxHunger) {
        super(display);
        this.maxHp = maxHp;
        this.maxHunger = maxHunger;
        this.health = maxHp;
        this.hunger = maxHunger;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHunger() {
        return this.hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }

    }

    public void eat(int food) {
        this.hunger += food;
        if (this.hunger > this.maxHunger) {
            this.hunger = this.maxHunger;
        }

    }

    public abstract void makeMove(GameMap var1, PathFinder var2, List<Entity> var3, List<Entity> var4, List<MoveRequest> var5);
}
