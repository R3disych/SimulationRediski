package elements.Animals;

import elements.Entity;
import gameMap.GameMap;
import elements.staticObjects.Grass;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import util.Coordinates;
import util.MoveRequest;
import util.PathFinder;

public class Herbivore extends Creature {
    public Herbivore() {
        super("H", 75, 75);
    }

    public Optional<Grass> findNearestGrass(GameMap map) {
    }

    public void eat(Grass grass) {
        super.eat(grass.getNutritionValue());
    }

    public void makeMove() {

    }
}
