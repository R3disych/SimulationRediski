package elements.staticObjects;

import elements.Alive;
import elements.Entity;
import util.Coordinates;

public class Grass extends Entity implements Alive {
    private static final int NUTRITION_VALUE = 20;
    private static final int MAX_HP = 1;
    private int Hp;

    public Grass(Coordinates coordinates) {
        super("G", coordinates);
        this.Hp = MAX_HP;
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
        return Hp;
    }

    @Override
    public int getMaxHp() {
        return 0;
    }

    @Override
    public boolean decreaseHp(int damage) {
        this.Hp -= damage;
        return Hp <= 0;
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
