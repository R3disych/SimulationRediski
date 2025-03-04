package elements.staticObjects;

import elements.Entity;
import util.Coordinates;

import java.awt.*;

public class Rock extends Entity {
    public Rock() {
        super("R");
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}