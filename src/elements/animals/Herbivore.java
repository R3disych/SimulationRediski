package elements.animals;

import elements.Alive;
import elements.staticObjects.Grass;
import util.Coordinates;


public class Herbivore extends Creature {
    private static final int MAX_HP = 50;
    private static final int MAX_HUNGER = 100;
    private static final int MAX_STAMINA = 3;
    private static final int DAMAGE = 1;

    public Herbivore() {
        super("H");
    }

    public Herbivore(Coordinates coordinates) {
        super("H");
        this.setCoordinates(coordinates);
    }

    @Override
    public int getMaxHp() {
        return MAX_HP;
    }

    @Override
    public int getNutritionalValue() {
        return getCurrentHp() / 2;
    }

    @Override
    public int getMaxHunger() {
        return MAX_HUNGER;
    }

    @Override
    public int getMaxStamina() {
        return MAX_STAMINA;
    }

    @Override
    public int getDamage() {
        return DAMAGE;
    }

    @Override
    public Class<? extends Alive> getVictimType() {
        return Grass.class;
    }

}
