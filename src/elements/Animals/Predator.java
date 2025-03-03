package elements.Animals;

import elements.Entity;
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

    public int getAttackDamage() {
        return this.attackDamage;
    }

    //public Optional<Herbivore> findNearestHerbivore(GameMap map) {
    //}

    public void attack(Herbivore herbivore) {
        herbivore.takeDamage(this.getAttackDamage());
    }

    public void eat(Herbivore herbivore) {
        this.setHealth(this.getHealth() + herbivore.getHealth() / 2);
        this.setHunger(this.getHunger() + herbivore.getHealth());
    }

    public void makeMove(GameMap map, PathFinder pathFinder) {
    }
}
