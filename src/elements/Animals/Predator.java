package elements.Animals;

import elements.Entity;
import elements.GameMap;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import util.Coordinates;
import util.MoveRequest;
import util.PathFinder;

public class Predator extends Creature {
    private final int attackDamage = 25;

    public Predator() {
        super("P", 100, 50);
    }

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public Optional<Herbivore> findNearestHerbivore(GameMap map) {
        List<Entity> entities = map.getEntitiesNear(this.getCoordinates());
        return entities.stream().filter((entity) -> {
            return entity instanceof Herbivore;
        }).map((entity) -> {
            return (Herbivore)entity;
        }).min(Comparator.comparingDouble((herbivore) -> {
            return this.getCoordinates().distanceTo(herbivore.getCoordinates());
        }));
    }

    public void attack(Herbivore herbivore) {
        herbivore.takeDamage(this.getAttackDamage());
    }

    public void eat(Herbivore herbivore) {
        this.setHealth(this.getHealth() + herbivore.getHealth() / 2);
        this.setHunger(this.getHunger() + herbivore.getHealth());
    }

    public void makeMove(GameMap map, PathFinder pathFinder, List<Entity> toRemove, List<Entity> toAdd, List<MoveRequest> moveRequests) {
        Optional<Herbivore> nearestHerbivore = this.findNearestHerbivore(map);
        if (nearestHerbivore.isPresent()) {
            Herbivore target = (Herbivore)nearestHerbivore.get();
            if (this.getCoordinates().distanceTo(target.getCoordinates()) <= 1.0) {
                this.attack(target);
                if (target.isDead()) {
                    this.eat(target);
                    toRemove.add(target);
                }
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
