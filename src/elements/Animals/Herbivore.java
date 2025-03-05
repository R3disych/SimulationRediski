package elements.Animals;

import elements.Entity;
import gameMap.GameMap;
import elements.staticObjects.Grass;

import java.util.ArrayList;
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

    public Herbivore(Coordinates coordinates) {
        super("H", 75, 75);
        this.setCoordinates(coordinates);
    }

    public Optional<Grass> findNearestGrass(GameMap map) {
        List<Entity> nearEntities = map.getEntitiesNear(this.getCoordinates(), 20);
        return nearEntities.stream()
                .filter(entity -> entity instanceof Grass)
                .map(entity -> (Grass) entity)
                .min(Comparator.comparingDouble(grass ->
                        this.getCoordinates().distanceTo(grass.getCoordinates())));
    }

    public void eat(Grass grass) {
        super.eat(grass.getNutritionValue());
    }

    public void makeMove(GameMap gameMap, PathFinder pathFinder) {
        while(isTurnable()) { //еще надо проверить на isDead() тут или в статическом методе makeTurn()
            Optional<Grass> nearestGrass = findNearestGrass(gameMap);
            if (nearestGrass.isPresent()) {
                Grass grass = nearestGrass.get();
                if (this.getCoordinates().distanceTo(grass.getCoordinates()) <= 1 && !grass.isDead()) {
                    this.eat(grass);
                    grass.die();
                    this.spendTurn();
                } else {
                    List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), grass.getCoordinates());
                    gameMap.moveEntity(this, path.get(1));
                    this.spendTurn();
                }
            } else {
                this.makeRandomMove(gameMap, pathFinder);
                this.spendTurn();
            }
        }
    }
}
