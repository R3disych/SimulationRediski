package elements.animals;

import elements.Entity;
import gameMap.GameMap;

import java.util.Random;

import util.Coordinates;
import util.PathFinder;

public abstract class Creature extends Entity {
    private int health;
    private int hunger;
    private final int maxHp;
    private final int maxHunger;
    private boolean isDead;
    private int turns;
    private int initiative;

    private Random rand = new Random();

    public Creature(String display, int maxHp, int maxHunger) {
        super(display);
        this.maxHp = maxHp;
        this.maxHunger = maxHunger;
        this.health = maxHp;
        this.hunger = maxHunger;
        this.isDead = false;
        this.turns = 3;
    }

    public void reinitInitiative() {
        this.initiative = rand.nextInt(100);
    }

    public int getInitiative() {
        return initiative;
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

    public void die() {
        this.isDead = true;
    }

    public int getTurns() {
        return this.turns;
    }

    public boolean isTurnable() {
        return this.turns > 0 && !this.isDead();
    }

    public void spendTurn() {
        this.turns--;
    }

    public void restoreTurns() {
        this.turns = 3;
    }

    @Override
    public boolean isObstacle() {
        return true;
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

    public void starve(int value) {
        this.hunger -= value;
    }

    public void starving() {
        this.health -= 15;
    }

    public abstract void makeMove(GameMap gameMap, PathFinder pathFinder);
}
