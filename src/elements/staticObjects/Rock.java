package elements.staticObjects;

import elements.Entity;

public class Rock extends Entity {
    public Rock() {
        super("R");
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}