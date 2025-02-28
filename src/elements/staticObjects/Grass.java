package elements.staticObjects;

import elements.Entity;

public class Grass extends Entity {
    private final int nutritionValue = 20;

    public Grass() {
        super("G");
    }

    public int getNutritionValue() {
        return this.nutritionValue;
    }
}
