package elements.animals;

import elements.Entity;
import gameMap.GameMap;
import util.Coordinates;
import util.PathFinder;

public class Footprints extends Creature {
    public Footprints(Coordinates coordinates) {
        super("F",50, 0);
        this.setCoordinates(coordinates);
    }

    @Override
    public void makeMove(GameMap gameMap, PathFinder pathFinder) {

    }
}
