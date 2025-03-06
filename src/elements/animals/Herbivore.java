package elements.animals;

import elements.Entity;
import gameMap.GameMap;
import elements.staticObjects.Grass;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import util.Coordinates;
import util.PathFinder;

public class Herbivore extends Creature {
    public Herbivore() {
        super("H", 75, 75);
    }

    public Herbivore(Coordinates coordinates) {
        super("H", 75, 75);
        this.setCoordinates(coordinates);
    }

    public Optional<Grass> findNearestGrass(PathFinder pathFinder) {
        List<Entity> nearEntities = pathFinder.getEntitiesNear(this.getCoordinates(), 20);
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
        Optional<Grass> nearestGrass = findNearestGrass(pathFinder);
        if (nearestGrass.isPresent()) {
            Grass grass = nearestGrass.get();
            if (this.getCoordinates().distanceTo(grass.getCoordinates()) <= 1 && !grass.isDead()) {
                this.eat(grass);
                grass.die();
                this.spendTurn();
            } else {
                List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), grass.getCoordinates());
                Coordinates newCoordinates = path.get(1);
                if (gameMap.getGameMapEntity().containsKey(newCoordinates)
                        && gameMap.getGameMapEntity().get(newCoordinates).isObstacle()
                        && !gameMap.getGameMapEntity().get(newCoordinates).isDead()) {
                    spendTurn();
                    return;
                }
                gameMap.moveEntity(this, newCoordinates);
                this.spendTurn();
            }
        } else {
            spendTurn();
        }
        this.starve(10);
        if (this.getHunger() <= 0) {
            this.starving();
        }
    }
}
