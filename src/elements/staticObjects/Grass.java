package elements.staticObjects;

import elements.Entity;
import util.Coordinates;

import java.awt.*;

public class Grass extends Entity {
    private final int nutritionValue = 20;
    private boolean isDead;

    public Grass() {
        super("G");
        this.isDead = false;
    }

    public Grass(Coordinates coordinates) {
        super("G", coordinates);
    }

    public int getNutritionValue() {
        return this.nutritionValue;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public void die() {
        this.isDead = true;
    }
}
