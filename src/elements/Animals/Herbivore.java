package elements.Animals;

import elements.Entity;
import elements.GameMap;
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
        List<Entity> entities = map.getEntitiesNear(this.getCoordinates());
        return entities.stream().filter((entity) -> {
            return entity instanceof Grass;
        }).map((entity) -> {
            return (Grass)entity;
        }).min(Comparator.comparingDouble((grass) -> {
            return this.getCoordinates().distanceTo(grass.getCoordinates());
        }));
    }

    public void eat(Grass grass) {
        this.eat(grass.getNutritionValue());
    }

    public void makeMove(GameMap map, PathFinder pathFinder, List<Entity> toRemove, List<Entity> toAdd, List<MoveRequest> moveRequests) {
        Optional<Grass> nearestGrass = this.findNearestGrass(map);
        if (nearestGrass.isPresent()) {
            Grass target = (Grass)nearestGrass.get();
            if (this.getCoordinates().distanceTo(target.getCoordinates()) <= 1.0) {
                this.eat(target);
                toRemove.add(target);
            } else {
                List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), target.getCoordinates());
                this.setPath(path);
                if (this.getPath() != null && this.getPath().size() > 1) {
                    Coordinates nextMove = (Coordinates)this.getPath().get(1);
                    moveRequests.add(new MoveRequest(this, nextMove));
                    this.getPath().remove(1);
                }
            }
        }

        this.setHunger(this.getHunger() - 1);
        if (this.getHunger() <= 0) {
            map.removeEntity(this);
        }

    }
}
