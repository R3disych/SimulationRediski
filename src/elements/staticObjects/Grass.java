package elements.staticObjects;

import elements.Entity;

public class Grass extends Entity {
    private final int nutritionValue = 20;
    private boolean isDead;

    public Grass() {
        super("G");
        this.isDead = false;
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
