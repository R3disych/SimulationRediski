package elements.animals;

import elements.Entity;
import gameMap.GameMap;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import util.Coordinates;
import util.PathFinder;

public class Predator extends Creature {
    private final int attackDamage = 25;

    public Predator() {
        super("P", 100, 50);
    }

    public Predator(Coordinates coordinates) {
        super("P", 100, 50);
        this.setCoordinates(coordinates);
    }

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public Optional<Herbivore> findNearestHerbivore(PathFinder pathFinder) {
        List<Entity> nearEntities = pathFinder.getEntitiesNear(this.getCoordinates(), 25);
        return nearEntities.stream()
                .filter(entity -> entity instanceof Herbivore)
                .map(entity -> (Herbivore) entity)
                .min(Comparator.comparingDouble(herbivore ->
                        this.getCoordinates().distanceTo(herbivore.getCoordinates())));
    }

    public void attack(Herbivore herbivore) {
        herbivore.takeDamage(this.getAttackDamage());
    }

    public void eat(Herbivore herbivore) {
        this.setHealth(this.getHealth() + herbivore.getHealth() / 2);
        this.setHunger(this.getHunger() + herbivore.getHealth());
    }

    public void makeMove(GameMap gameMap, PathFinder pathFinder) {
        Optional<Herbivore> nearestHerbivore = findNearestHerbivore(pathFinder);
        if (nearestHerbivore.isPresent()) {
            Herbivore herbivore = nearestHerbivore.get();
            if (this.getCoordinates().distanceTo(herbivore.getCoordinates()) <= 1 && !herbivore.isDead()) {
                this.attack(herbivore);
                this.spendTurn();
                if (this.getCoordinates().distanceTo(herbivore.getCoordinates()) <= 1 && herbivore.isDead()) {
                    this.eat(herbivore);
                    this.spendTurn();
                }
            } else {
                List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), herbivore.getCoordinates());
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
