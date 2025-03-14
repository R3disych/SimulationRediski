package elements.animals;

import elements.Alive;
import util.Coordinates;


public class Predator extends Creature {
    private static final int MAX_HP = 75;
    private static final int MAX_HUNGER = 50;
    private static final int MAX_STAMINA = 3;
    private static final int DAMAGE = 25;

    public Predator(Coordinates coordinates) {
        super(coordinates);
        this.setCoordinates(coordinates);
    }

    @Override
    public int getMaxHp() {
        return MAX_HP;
    }

    @Override
    public int getNutritionalValue() {
        return 50;
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
        return Herbivore.class;
    }
}
