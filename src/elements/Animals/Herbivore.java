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
        int i = 5;
        if(i < 1) {
            System.out.println("Законились ходы");
        }
        while(i > 0) { //еще надо проверить на isDead() тут или в статическом методе makeTurn()
            Optional<Grass> nearGrass = findNearestGrass(gameMap);
            if (nearGrass.isPresent()) {
                Grass grass = nearGrass.get();
                if (this.getCoordinates().distanceTo(grass.getCoordinates()) <= 1 && !grass.isDead()) {
                    this.eat(grass);
                    grass.die();
                    System.out.println("Овца съела траву!");
                    this.spendTurn();
                    i--;
                } else {
                    List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), grass.getCoordinates());
                    gameMap.moveEntity(this, path.get(1));
                    this.spendTurn();
                    i--;
                }
            } else {
                this.makeRandomMove(gameMap, pathFinder);
                this.spendTurn();
                i--;
                System.out.println("Current coords of herbivore1: " + this.getCoordinates().getWidth() + ", " + this.getCoordinates().getHeight());
            }
        }
    }
}
