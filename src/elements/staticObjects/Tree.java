package elements.staticObjects;

import elements.Entity;

public class Tree extends Entity {
    public Tree() {
        super("T");
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
