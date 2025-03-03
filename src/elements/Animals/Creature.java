package elements.Animals;

import elements.Entity;
import gameMap.GameMap;
import java.util.List;
import java.util.Random;

import util.Coordinates;
import util.MoveRequest;
import util.PathFinder;

import java.util.Random;

public abstract class Creature extends Entity {
    private int health;
    private int hunger;
    private final int maxHp;
    private final int maxHunger;
    private boolean isDead;
    private int turns;
    private boolean isTurnable; //наверное лишнее

    public Creature(String display, int maxHp, int maxHunger) {
        super(display);
        this.maxHp = maxHp;
        this.maxHunger = maxHunger;
        this.health = maxHp;
        this.hunger = maxHunger;
        this.isDead = false;
        this.turns = 5;
        this.isTurnable = true;
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

    public int getTurns() {
        return this.turns;
    }

    public boolean isTurnable() {
        return this.turns <= 0;
    }

    public void spendTurn() {
        this.turns--;
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

    public void makeRandomMove(GameMap map, PathFinder pathFinder) {
        Random rand = new Random();

        Coordinates currentCoords = this.getCoordinates();
        int currX = currentCoords.getWidth();
        int currY = currentCoords.getHeight();

        int[] dx = {0, 0, -1, 1, -1, -1, 1, 1};
        int[] dy = {-1, 1, 0, 0, -1, 1, -1, 1};
        int newX = currX + dx[rand.nextInt(dx.length)];
        int newY = currY + dy[rand.nextInt(dy.length)];

        Coordinates newCoordinates = new Coordinates(newX, newY);

        if (pathFinder.isValid(newCoordinates) && pathFinder.isWalkable(newCoordinates)) {
            map.moveEntity(this, newCoordinates);
        } else {
            makeRandomMove(map, pathFinder);
        }
    }

    public abstract void makeMove(GameMap gameMap, PathFinder pathFinder);
}
