package elements.staticObjects;

import elements.Entity;
import util.Coordinates;

public class Tree extends Entity {
    public Tree() {
        super("T");
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
