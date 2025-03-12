package elements.staticObjects;

import elements.Entity;
import util.Coordinates;

public class Tree extends Entity {
    public Tree(Coordinates coordinates) {
        super("T", coordinates);
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
