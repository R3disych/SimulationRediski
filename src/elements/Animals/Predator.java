package elements.Animals;

import elements.Entity;
import elements.staticObjects.Grass;
import gameMap.GameMap;
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

    public Predator(Coordinates coordinates) {
        super("P", 100, 50);
        this.setCoordinates(coordinates);
    }

    public int getAttackDamage() {
        return this.attackDamage;
    }

    public Optional<Herbivore> findNearestHerbivore(GameMap map) {
        List<Entity> nearEntities = map.getEntitiesNear(this.getCoordinates(), 25);
        return nearEntities.stream()
                .filter(entity -> entity instanceof Herbivore)
                .map(entity -> (Herbivore) entity)
                .min(Comparator.comparingDouble(herbivore ->
                        this.getCoordinates().distanceTo(herbivore.getCoordinates())));
    }

    public void attack(Herbivore herbivore) {
        herbivore.takeDamage(this.getAttackDamage());
        if(herbivore.isDead()) {
            herbivore.die();
        }
    }

    public void eat(Herbivore herbivore) {
        this.setHealth(this.getHealth() + herbivore.getHealth() / 2);
        this.setHunger(this.getHunger() + herbivore.getHealth());
    }

    public void makeMove(GameMap gameMap, PathFinder pathFinder) {
        while(isTurnable()) { //еще надо проверить на isDead() тут или в статическом методе makeTurn()
        Optional<Herbivore> nearestHerbivore = findNearestHerbivore(gameMap);
        if (nearestHerbivore.isPresent()) {
            Herbivore herbivore = nearestHerbivore.get();
            if (this.getCoordinates().distanceTo(herbivore.getCoordinates()) <= 1 && !herbivore.isDead()) {
                this.attack(herbivore);

                if(this.getCoordinates().distanceTo(herbivore.getCoordinates()) <= 1 && herbivore.isDead()) {
                    this.eat(herbivore);
                }

                this.spendTurn();
            } else {
                List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), herbivore.getCoordinates());
                gameMap.moveEntity(this, path.get(1));
                this.spendTurn();
            }
        } else {
            this.makeRandomMove(gameMap, pathFinder);
            this.spendTurn();
            spendTurn();
            System.out.println("Current coords of herbivore1: " + this.getCoordinates().getWidth() + ", " + this.getCoordinates().getHeight());
        }
    }
        if(!isTurnable()) {
            System.out.println("Законились ходы");
        }
    }
}
