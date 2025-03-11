package elements.staticObjects;

import elements.Alive;
import elements.Entity;
import util.Coordinates;

import java.awt.*;

public class Grass extends Entity implements Alive {
    private final int NUTRITION_VALUE = 20;
    private int Hp;

    public Grass() {
        super("G");
    }

    public Grass(Coordinates coordinates) {
        super("G", coordinates);
        this.Hp = 1;
    }

    @Override
    public int getCurrentHunger() {
        return 0;
    }

    @Override
    public int getMaxHunger() {
        return 0;
    }

    @Override
    public int getCurrentHp() {
        return 0;
    }

    @Override
    public int getMaxHp() {
        return 0;
    }

    @Override
    public boolean decreaseHp(int damage) {
        return false;
    }

    @Override
    public boolean decraseHunger() {
        return false;
    }

    @Override
    public boolean isDead() {
        return this.Hp <= 0;
    }

    @Override
    public void starving() {

    }

    @Override
    public int getNutritionalValue() {
        return NUTRITION_VALUE;
    }

    @Override
    public void eat(int nutritionValue) {
    }
}
